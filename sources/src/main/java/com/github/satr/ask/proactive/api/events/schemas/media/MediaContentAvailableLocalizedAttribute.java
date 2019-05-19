package com.github.satr.ask.proactive.api.events.schemas.media;
// Copyright © 2019, github.com/satr, MIT License

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.satr.ask.proactive.api.events.EventLocalizedAttribute;

import java.util.Locale;

public class MediaContentAvailableLocalizedAttribute extends EventLocalizedAttribute {
    @JsonProperty("providerName") private String providerName;
    @JsonProperty("contentName") private String contentName;

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public String getContentName() {
        return contentName;
    }

    public void setContentName(String contentName) {
        this.contentName = contentName;
    }

    public MediaContentAvailableLocalizedAttribute withProviderName(String providerName) {
        setProviderName(providerName);
        return this;
    }

    public MediaContentAvailableLocalizedAttribute withContentName(String contentName) {
        setContentName(contentName);
        return this;
    }

    public MediaContentAvailableLocalizedAttribute withLocale(Locale locale) {
        setLocale(locale);
        return this;
    }
}
