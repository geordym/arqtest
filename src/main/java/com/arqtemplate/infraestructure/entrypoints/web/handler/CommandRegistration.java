package com.arqtemplate.infraestructure.entrypoints.web.handler;

import com.arqtemplate.infraestructure.entrypoints.web.dto.CommandContext;

import java.util.function.BiConsumer;

public final class CommandRegistration<C, R> {
    final Class<C> payloadType;
    final Class<R> responseType;
    final CommandHandler<C, R> handler;

    public CommandRegistration(
            Class<C> payloadType,
            Class<R> responseType,
            CommandHandler<C, R> handler
    ) {
        this.payloadType = payloadType;
        this.responseType = responseType;
        this.handler = handler;
    }
}

