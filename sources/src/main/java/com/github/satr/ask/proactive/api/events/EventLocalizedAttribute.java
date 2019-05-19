package com.github.satr.ask.proactive.api.events;
// Copyright © 2019, github.com/satr, MIT License

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Locale;

public class EventLocalizedAttribute {
    @JsonProperty("locale") private String locale;

    public EventLocalizedAttribute() {
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public void setLocale(Locale locale) {
        setLocale(locale.toLanguageTag());
    }
}
