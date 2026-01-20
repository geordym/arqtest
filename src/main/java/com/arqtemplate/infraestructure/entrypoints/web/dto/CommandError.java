package com.arqtemplate.infraestructure.entrypoints.web.dto;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Serdeable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommandError {

    private String code;
    private String message;

    public static CommandError fromException(Exception ex) {
        return new CommandError(
                ex.getClass().getSimpleName(),
                ex.getMessage()
        );
    }
}