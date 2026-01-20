package com.arqtemplate.infraestructure.entrypoints.web.dto;

import java.util.Map;

public class CommandContext {
    private final Map<String, Object> metadata;

    public CommandContext(Map<String, Object> metadata) {
        this.metadata = metadata;
    }

    public String traceId() {
        return (String) metadata.get("traceId");
    }

    public Object get(String key) {
        return metadata.get(key);
    }
}
