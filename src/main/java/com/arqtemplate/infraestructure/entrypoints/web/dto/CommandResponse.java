package com.arqtemplate.infraestructure.entrypoints.web.dto;

import io.micronaut.serde.annotation.Serdeable;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Serdeable
@Builder
public class CommandResponse<T>  {
    private CommandStatus status;
    private T data;
    private List<CommandError> errors;
    private CommandMeta meta;

    public static <T> CommandResponse<T> success(
            T data,
            CommandMeta meta
    ) {
        return CommandResponse.<T>builder()
                .status(CommandStatus.SUCCESS)
                .data(data)
                .errors(List.of())
                .meta(meta)
                .build();
    }

    public static CommandResponse<Void> failure(
            String command,
            List<CommandError> errors,
            CommandMeta meta
    ) {
        return CommandResponse.<Void>builder()
                .status(CommandStatus.FAILED)
                .data(null)
                .errors(errors)
                .meta(meta)
                .build();
    }
}
