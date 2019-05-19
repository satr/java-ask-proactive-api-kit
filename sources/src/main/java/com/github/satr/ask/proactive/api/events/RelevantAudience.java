package com.github.satr.ask.proactive.api.events;
// Copyright © 2019, github.com/satr, MIT License

import com.fasterxml.jackson.annotation.JsonProperty;

public class RelevantAudience {
    @JsonProperty("type") private RelevantAudienceType type = RelevantAudienceType.Multicast;
    @JsonProperty("payload") private RelevantAudiencePayload payload;//For Multicast - should be either null or an empty object

    public RelevantAudienceType getType() {
        return type;
    }

    public void setType(RelevantAudienceType type) {
        this.type = type;
    }

    public RelevantAudiencePayload getPayload() {
        return payload;
    }

    public void setPayload(RelevantAudiencePayload payload) {
        this.payload = payload;
    }
}
