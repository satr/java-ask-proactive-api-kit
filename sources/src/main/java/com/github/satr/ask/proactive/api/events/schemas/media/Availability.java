package com.github.satr.ask.proactive.api.events.schemas.media;
// Copyright © 2019, github.com/satr, MIT License

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.satr.ask.proactive.api.events.EventProvider;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Availability {
    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
    @JsonProperty("startTime") private String startTime;
    @JsonProperty("provider") private EventProvider provider = EventProvider.fromLocalizedAttribute();
    @JsonProperty("method") private DistributionMethod method;

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        setStartTime(dateTimeFormatter.format(startTime));
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public EventProvider getProvider() {
        return provider;
    }

    public void setProvider(EventProvider provider) {
        this.provider = provider;
    }

    public void setProvider(String providerName) {
        EventProvider eventProvider = new EventProvider();
        eventProvider.setName(providerName);
        setProvider(eventProvider);
    }

    public DistributionMethod getMethod() {
        return method;
    }

    public void setMethod(DistributionMethod method) {
        this.method = method;
    }

    public Availability withStartTime(LocalDateTime startTime) {
        setStartTime(startTime);
        return this;
    }

    public Availability withStartTime(String startTime) {
        setStartTime(startTime);
        return this;
    }

    public Availability withProvider(String providerName) {
        setProvider(providerName);
        return this;
    }

    public Availability withProviderFromLocalizedAttribute() {
        setProvider(EventProvider.fromLocalizedAttribute());
        return this;
    }

    public Availability withMethod(DistributionMethod method) {
        setMethod(method);
        return this;
    }
}
