package com.arqtemplate.infraestructure.entrypoints.web.handler;

import com.arqtemplate.infraestructure.entrypoints.web.dto.CommandContext;
import com.arqtemplate.infraestructure.entrypoints.web.dto.CommandResponse;
import io.micronaut.http.HttpResponse;

@FunctionalInterface
public interface CommandHandler<C> {
    HttpResponse<?> handle(C command);
}
