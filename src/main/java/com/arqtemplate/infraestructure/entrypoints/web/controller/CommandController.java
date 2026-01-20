package com.arqtemplate.infraestructure.entrypoints.web.controller;

import com.arqtemplate.infraestructure.entrypoints.web.dto.CommandRequestDto;
import com.arqtemplate.infraestructure.entrypoints.web.dto.CommandResponse;
import com.arqtemplate.infraestructure.entrypoints.web.handler.CommandBus;
import com.arqtemplate.infraestructure.entrypoints.web.handler.HelloCommandHandler;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.serde.ObjectMapper;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@Controller("/commands")
public class CommandController {

    private static final Logger log =
            LoggerFactory.getLogger(CommandController.class);

    @Inject
    private ObjectMapper objectMapper;

    @Inject
    private HelloCommandHandler handler;

    @Inject
    private CommandBus commandBus;

    @Post
    public CommandResponse<?> receiveCommand(
            @Body CommandRequestDto command
    ) {
        return commandBus.dispatch(command);
    }


}
