package com.github.satr.ask.proactive.api.events.schemas.media;
// Copyright © 2019, github.com/satr, MIT License

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreativeWork {
    @JsonProperty("name") private String name;
    @JsonProperty("contentType") private MediaTypes contentType;

    public static CreativeWork fromLocalizedAttribute() {
        CreativeWork creativeWork = new CreativeWork();
        creativeWork.withNameFromLocalizedAttribute();
        return creativeWork;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MediaTypes getContentType() {
        return contentType;
    }

    public void setContentType(MediaTypes contentType) {
        this.contentType = contentType;
    }

    public CreativeWork withName(String name) {
        setName(name);
        return this;
    }

    public CreativeWork withNameFromLocalizedAttribute() {
        setName("localizedattribute:contentName");
        return this;
    }

    public CreativeWork withContentType(MediaTypes contentType) {
        setContentType(contentType);
        return this;
    }
}
