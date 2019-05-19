package com.github.satr.ask.proactive.api.events;
// Copyright © 2019, github.com/satr, MIT License

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.satr.ask.proactive.api.events.schemas.EventSchema;

public class Event {
    @JsonProperty("name") private String name;
    @JsonProperty("payload") private EventSchema payload;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EventSchema getPayload() {
        return payload;
    }

    public void setPayload(EventSchema eventSchema) {
        this.payload = eventSchema;
    }
}

