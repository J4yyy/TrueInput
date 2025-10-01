package de.bitbright.util;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class GenRes {
    @JsonProperty(index = 1)
    private final String timestamp;
    @JsonProperty(index = 2)
    private final String message;
    @JsonProperty(index = 3)
    private final Object data;

    private final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yy - HH:mm:ss");

    public GenRes(Object data) {
        LocalDateTime now = LocalDateTime.now();
        this.timestamp = now.format(FORMATTER);
        this.message = "success";
        this.data = data;
    }

    public GenRes(Object data, String message) {
        LocalDateTime now = LocalDateTime.now();
        this.timestamp = now.format(FORMATTER);
        this.message = message;
        this.data = data;
    }

    public String getTimestamp() {
        return this.timestamp;
    }

    public String getMessage() {
        return this.message;
    }

    public Object getData() {
        return this.data;
    }
}