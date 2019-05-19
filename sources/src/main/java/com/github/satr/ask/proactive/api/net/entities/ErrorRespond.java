package com.github.satr.ask.proactive.api.net.entities;
// Copyright © 2019, github.com/satr, MIT License

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ErrorRespond {
    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
    private final LocalDateTime createdOn;
    @JsonProperty("error_description") private String errorDescription;
    @JsonProperty("error") private String error;

    public ErrorRespond() {
        createdOn = LocalDateTime.now();
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public String createdOnAsString() {
        return dateTimeFormatter.format(createdOn);
    }
}
