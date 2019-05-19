package com.github.satr.ask.proactive.api.events.schemas.media;
// Copyright © 2019, github.com/satr, MIT License

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.satr.ask.proactive.api.events.EventLocalizedAttribute;
import com.github.satr.ask.proactive.api.events.schemas.EventSchema;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MediaContentAvailable implements EventSchema {
    @JsonProperty("availability") private Availability availability = new Availability();
    @JsonProperty("content") private CreativeWork content = CreativeWork.fromLocalizedAttribute();

    @Override
    public String getName() {
        return "AMAZON.MediaContent.Available";
    }

    public MediaContentAvailableLocalizedAttribute getLocalizedAttribute(Locale locale) {
        MediaContentAvailableLocalizedAttribute attribute = new MediaContentAvailableLocalizedAttribute();
        attribute.setLocale(locale);
        return attribute;
    }

    public List<EventLocalizedAttribute> getLocalizedAttributes(String providerName, String contentName, Locale... locale) {
        ArrayList<EventLocalizedAttribute> attributes = new ArrayList<>();
        for (Locale loc: locale) {
            attributes.add(new MediaContentAvailableLocalizedAttribute()
                                    .withLocale(loc)
                                    .withProviderName(providerName)
                                    .withContentName(contentName));
        }
        return attributes;
    }

    public Availability getAvailability() {
        return availability;
    }

    public void setAvailability(Availability availability) {
        this.availability = availability;
    }

    public CreativeWork getContent() {
        return content;
    }

    public void setContent(CreativeWork content) {
        this.content = content;
    }
}
