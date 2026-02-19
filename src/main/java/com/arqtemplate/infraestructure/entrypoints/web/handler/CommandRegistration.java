package com.arqtemplate.infraestructure.entrypoints.web.handler;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;

public final class CommandRegistration<C> {
    final CommandHandler<C> handler;

    public CommandRegistration(
            CommandHandler<C> handler
    ) {
        this.handler = handler;
    }
}

