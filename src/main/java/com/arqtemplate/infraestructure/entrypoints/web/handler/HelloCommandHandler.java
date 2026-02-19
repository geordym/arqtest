package com.arqtemplate.infraestructure.entrypoints.web.handler;

import com.arqtemplate.infraestructure.entrypoints.web.dto.CommandContext;
import com.arqtemplate.infraestructure.entrypoints.web.dto.CommandResponse;
import com.arqtemplate.infraestructure.entrypoints.web.dto.HelloCommandPayload;
import com.arqtemplate.infraestructure.entrypoints.web.dto.HelloResult;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import jakarta.inject.Singleton;

@Singleton
public class HelloCommandHandler {

    public HttpResponse<?> procesar(HttpRequest<?> request) {
        return HttpResponse.ok();
    }

}
