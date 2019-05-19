package com.github.satr.ask.proactive.api.events;
// Copyright © 2019, github.com/satr, MIT License

import com.fasterxml.jackson.annotation.JsonProperty;

public class EventProvider {
    @JsonProperty("name") private String name;

    public static EventProvider fromLocalizedAttribute() {
        EventProvider eventProvider = new EventProvider();
        eventProvider.setName("localizedattribute:providerName");
        return eventProvider;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
