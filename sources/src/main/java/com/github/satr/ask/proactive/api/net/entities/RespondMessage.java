package com.github.satr.ask.proactive.api.net.entities;
// Copyright © 2019, github.com/satr, MIT License

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RespondMessage {
    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
    private final LocalDateTime createdOn = LocalDateTime.now();
    @JsonProperty("type") private String type;
    @JsonProperty("message") private String message;

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String createdOnAsString() {
        return dateTimeFormatter.format(createdOn);
    }
}
