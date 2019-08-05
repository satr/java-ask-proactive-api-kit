package com.github.satr.ask.proactive.api.net.entities;
// Copyright © 2019, github.com/satr, MIT License

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.satr.common.DateTimeUtil;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

public class ErrorRespond {
    private final OffsetDateTime createdOn;
    @JsonProperty("error_description") private String errorDescription;
    @JsonProperty("error") private String error;
    @JsonProperty("error_index") private String errorIndex;

    public ErrorRespond() {
        createdOn = OffsetDateTime.now();
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

    public OffsetDateTime getCreatedOn() {
        return createdOn;
    }

    public String createdOnAsString() {
        return DateTimeUtil.toIsoString(createdOn);
    }

    public String getErrorIndex() { return errorIndex; }

    public void setErrorIndex(String errorIndex) { this.errorIndex = errorIndex; }
}
