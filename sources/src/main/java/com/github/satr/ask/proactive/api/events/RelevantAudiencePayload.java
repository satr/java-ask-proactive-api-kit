package com.github.satr.ask.proactive.api.events;
// Copyright © 2019, github.com/satr, MIT License

import com.fasterxml.jackson.annotation.JsonProperty;

public class RelevantAudiencePayload {
    @JsonProperty("user") private String amzn1AskAccountId;

    public String getUser() {
        return amzn1AskAccountId;
    }

    public void setUser(String amzn1AskAccountId) {
        this.amzn1AskAccountId = amzn1AskAccountId;
    }
}
