package com.arqtemplate.infraestructure.entrypoints.web.dto;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public record HelloResult (String userId){}
