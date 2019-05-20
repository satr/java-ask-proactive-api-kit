package com.github.satr.ask.proactive.api.net.entities;
// Copyright © 2019, github.com/satr, MIT License

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.satr.common.DateTimeUtil;
import java.time.OffsetDateTime;

public class RespondMessage {
    private final OffsetDateTime createdOn = OffsetDateTime.now();
    @JsonProperty("type") private String type;
    @JsonProperty("message") private String message;

    public OffsetDateTime getCreatedOn() {
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
        return DateTimeUtil.toIsoString(createdOn);
    }
}
