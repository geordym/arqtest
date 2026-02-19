package com.arqtemplate.infraestructure.entrypoints.web.handler;

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
                        handler::procesar
                )
                .build();
    }
}
