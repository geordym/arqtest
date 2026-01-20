package com.arqtemplate.infraestructure.entrypoints.web.handler;

import com.arqtemplate.infraestructure.entrypoints.web.dto.HelloCommandPayload;
import com.arqtemplate.infraestructure.entrypoints.web.dto.HelloResult;
import io.micronaut.context.annotation.Factory;
import io.micronaut.serde.ObjectMapper;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

@Factory
public class CommandBusFactory {

    @Inject
    private HelloCommandHandler handler;

    @Singleton
    CommandBus commandBus(
            ObjectMapper objectMapper
    ) {
        return CommandBus.builder(objectMapper)
                .addCommandListener(
                        "users.create",
                        HelloCommandPayload.class,
                        HelloResult.class,
                        (cmd, ctx) -> handler.procesar(cmd, ctx)
                )
                .build();
    }
}
