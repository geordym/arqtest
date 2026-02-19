package com.arqtemplate.infraestructure.entrypoints.web.handler;

import com.arqtemplate.infraestructure.entrypoints.web.dto.*;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.json.tree.JsonNode;
import io.micronaut.serde.ObjectMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandBus {
    private final Map<String, CommandRegistration<?>> registry;
    private final ObjectMapper objectMapper;

    private CommandBus(
            Map<String, CommandRegistration<?>> registry,
            ObjectMapper objectMapper
    ) {
        this.registry = registry;
        this.objectMapper = objectMapper;
    }

    public static Builder builder(ObjectMapper objectMapper) {
        return new Builder(objectMapper);
    }


    public HttpResponse<?> dispatch(HttpRequest<JsonNode> request) {
        JsonNode body = request.getBody().orElse(null);
        if (body == null) {
            return HttpResponse.badRequest("Body vacío");
        }

        String name = body.get("name").getStringValue();
        if (name == null) {
            return HttpResponse.badRequest();
        }

        CommandRegistration<?> reg = registry.get(name);
        if (reg == null) {
            return HttpResponse.notFound();
        }

        try {
            return invoke(reg, request);
        } catch (Exception e) {
           return HttpResponse.serverError();
        }
    }

    @SuppressWarnings("unchecked")
    private <C, R> HttpResponse<?> invoke(
            CommandRegistration<C> reg,
            HttpRequest<?> request
    ) {
        return reg.handler.handle((C) request);
    }


    // ================= BUILDER =================

    public static class Builder {

        private final Map<String, CommandRegistration<?>> registry= new HashMap<>();
        private final ObjectMapper objectMapper;

        private Builder(ObjectMapper objectMapper) {
            this.objectMapper = objectMapper;
        }

        public <C, R> Builder addCommandListener(
                String commandName,
                CommandHandler<C> handler
        ) {
            if (registry.containsKey(commandName)) {
                throw new IllegalStateException(
                        "Duplicate command: " + commandName
                );
            }

            registry.put(
                    commandName,
                    new CommandRegistration<>(handler)
            );
            return this;
        }

        public CommandBus build() {
            return new CommandBus(
                    Map.copyOf(registry),
                    objectMapper
            );
        }
    }
}
