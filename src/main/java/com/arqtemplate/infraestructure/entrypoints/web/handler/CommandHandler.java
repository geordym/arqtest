package com.arqtemplate.infraestructure.entrypoints.web.handler;

import com.arqtemplate.infraestructure.entrypoints.web.dto.CommandContext;
import com.arqtemplate.infraestructure.entrypoints.web.dto.CommandResponse;

@FunctionalInterface
public interface CommandHandler<C, R> {
    CommandResponse<R> handle(C command, CommandContext ctx);
}
