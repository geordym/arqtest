package com.arqtemplate.infraestructure.entrypoints.web.handler;

import com.arqtemplate.infraestructure.entrypoints.web.dto.CommandContext;
import com.arqtemplate.infraestructure.entrypoints.web.dto.CommandResponse;
import com.arqtemplate.infraestructure.entrypoints.web.dto.HelloCommandPayload;
import com.arqtemplate.infraestructure.entrypoints.web.dto.HelloResult;
import jakarta.inject.Singleton;

@Singleton
public class HelloCommandHandler {

    public CommandResponse<HelloResult> procesar(HelloCommandPayload command, CommandContext ctx) {
        System.out.println(
                "Hello " + command.getName()

        );

        return CommandResponse.success(null, null);
    }

}
