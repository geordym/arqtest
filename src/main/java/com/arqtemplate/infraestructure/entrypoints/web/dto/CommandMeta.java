package com.arqtemplate.infraestructure.entrypoints.web.dto;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Map;

@Serdeable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommandMeta {

    private String traceId;
    private Instant timestamp;

    public static CommandMeta from(Map<String, Object> metadata) {
        return new CommandMeta(
                (String) metadata.getOrDefault("trace_id", "NO_TRACEID"),
                Instant.now()
        );
    }
}
