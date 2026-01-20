package com.arqtemplate.infraestructure.entrypoints.web.controller;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller("/hello")
public class HelloController {

    private static final Logger log =
            LoggerFactory.getLogger(HelloController.class);


    @Get
    public String hello(String name) {
        log.info("INFO - Test log message");
        log.warn("WARN - Test warning message");
        log.error("ERROR - Test error message");

        return "Hello World";
    }

}