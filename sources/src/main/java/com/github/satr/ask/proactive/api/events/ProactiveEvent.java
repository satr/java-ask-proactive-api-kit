package com.github.satr.ask.proactive.api.events;
// Copyright © 2019, github.com/satr, MIT License

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.satr.ask.proactive.api.events.schemas.EventSchema;
import com.github.satr.common.DateTimeUtil;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAmount;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ProactiveEvent {
    @JsonProperty("timestamp") private String timestamp = setUniqueReferenceId();
    @JsonProperty("referenceId") private String referenceId;
    @JsonProperty("expiryTime") private String expiryTime;
    @JsonProperty("event") private Event event = new Event();
    @JsonProperty("localizedAttributes") private List<EventLocalizedAttribute> localizedAttributes = new ArrayList<>();
    @JsonProperty("relevantAudience") private RelevantAudience relevantAudience = new RelevantAudience();

    public ProactiveEvent() {
        setTimestampNew();
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public void setTimestampNew() {
        setTimestamp(DateTimeUtil.toIsoString(DateTimeUtil.utcNow()));
    }

    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }

    public String getExpiryTime() {
        return expiryTime;
    }

    // expiryTime should be at least 5 minutes in the future and no more than 24 hours after the current time
    public void setExpiryTime(String expiryTime) {
        this.expiryTime = expiryTime;
    }

    // expiryTime should be at least 5 minutes in the future and no more than 24 hours after the current time
    public void setExpiryTime(OffsetDateTime expiryTime) {
        setExpiryTime(DateTimeUtil.toIsoString(DateTimeUtil.toUtc(expiryTime)));
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public List<EventLocalizedAttribute> getLocalizedAttributes() {
        return localizedAttributes;
    }

    public void setLocalizedAttributes(List<EventLocalizedAttribute> localizedAttributes) {
        this.localizedAttributes = localizedAttributes;
    }

    public RelevantAudience getRelevantAudience() {
        return relevantAudience;
    }

    public void setRelevantAudience(RelevantAudience relevantAudience) {
        this.relevantAudience = relevantAudience;
    }

    public void setExpiryTimeNowPlus(TemporalAmount period) {
        setExpiryTime(OffsetDateTime.now().plus(period));
    }

    public void setExpiryTimeNowPlusSeconds(int seconds) {
        setExpiryTime(OffsetDateTime.now().plusSeconds(seconds));
    }

    public void setExpiryTimeNowPlusMinutes(int minutes) {
        setExpiryTime(OffsetDateTime.now().plusMinutes(minutes));
    }

    public void setExpiryTimeNowPlusHours(int hours) {
        setExpiryTime(OffsetDateTime.now().plusHours(hours));
    }

    public void setExpiryTimeNowPlusDays(int days) {
        setExpiryTime(OffsetDateTime.now().plusDays(days));
    }

    public void setExpiryTimeNowPlusWeeks(int weeks) {
        setExpiryTime(OffsetDateTime.now().plusWeeks(weeks));
    }

    public void setExpiryTimeNowPlusMonths(int months) {
        setExpiryTime(OffsetDateTime.now().plusMonths(months));
    }

    public void setExpiryTimeNowPlusYears(int years) {
        setExpiryTime(OffsetDateTime.now().plusYears(years));
    }

    public void addLocalizedAttribute(EventLocalizedAttribute localizedAttribute) {
        //TODO check if an attribute with such Locale already exist - replace
        getLocalizedAttributes().add(localizedAttribute);
    }

    public void setEventWithSchema(EventSchema eventSchema) {
        Event event = new Event();
        event.setPayload(eventSchema);
        event.setName(eventSchema.getName());
        setEvent(event);
    }

    public String setUniqueReferenceId() {
        String uuid = UUID.randomUUID().toString();
        setReferenceId(uuid);
        return uuid;
    }

    public void addLocalizedAttributes(List<EventLocalizedAttribute> localizedAttributes) {
        for (EventLocalizedAttribute attribute : localizedAttributes)
            addLocalizedAttribute(attribute);
    }
}
