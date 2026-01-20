package com.arqtemplate.infraestructure.entrypoints.web.dto;

import io.micronaut.serde.annotation.Serdeable;
import lombok.Data;

@Data
@Serdeable
public class CommandRequestDto {
    private String name;
    private Object payload;
}
