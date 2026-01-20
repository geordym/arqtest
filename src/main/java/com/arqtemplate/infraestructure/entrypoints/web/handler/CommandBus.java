package com.arqtemplate.infraestructure.entrypoints.web.handler;

import com.arqtemplate.infraestructure.entrypoints.web.dto.*;
import io.micronaut.serde.ObjectMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandBus {
    private final Map<String, CommandRegistration<?, ?>> registry;
    private final ObjectMapper objectMapper;

    private CommandBus(
            Map<String, CommandRegistration<?, ?>> registry,
            ObjectMapper objectMapper
    ) {
        this.registry = registry;
        this.objectMapper = objectMapper;
    }

    public static Builder builder(ObjectMapper objectMapper) {
        return new Builder(objectMapper);
    }

    public CommandResponse<?> dispatch(CommandRequestDto request) {
        CommandRegistration<?, ?> reg = registry.get(request.getName());

        if (reg == null) {
            return CommandResponse.failure(
                    request.getName(),
                    List.of(new CommandError(
                            "COMMAND_NOT_FOUND",
                            "Command not registered"
                    )),
                    null
            );
        }

        CommandMeta commandMeta = null;
        try {
            Object payload = objectMapper.readValue(
                    objectMapper.writeValueAsBytes(request.getPayload()),
                    reg.payloadType);


            return invoke(reg, payload, null);

        } catch (Exception e) {
            return CommandResponse.failure(
                    request.getName(),
                    List.of(CommandError.fromException(e)),
                    commandMeta
            );
        }
    }

    @SuppressWarnings("unchecked")
    private <C, R> CommandResponse<R> invoke(
            CommandRegistration<C, R> reg,
            Object payload,
            CommandContext ctx
    ) {
        return reg.handler.handle((C) payload, ctx);
    }


    // ================= BUILDER =================

    public static class Builder {

        private final Map<String, CommandRegistration<?, ?>> registry= new HashMap<>();
        private final ObjectMapper objectMapper;

        private Builder(ObjectMapper objectMapper) {
            this.objectMapper = objectMapper;
        }

        public <C, R> Builder addCommandListener(
                String commandName,
                Class<C> payloadType,
                Class<R> responseType,
                CommandHandler<C, R> handler
        ) {
            if (registry.containsKey(commandName)) {
                throw new IllegalStateException(
                        "Duplicate command: " + commandName
                );
            }

            registry.put(
                    commandName,
                    new CommandRegistration<>(payloadType, responseType, handler)
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
