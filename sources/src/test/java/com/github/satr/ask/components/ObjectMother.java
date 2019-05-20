package com.github.satr.ask.components;

import com.github.satr.ask.proactive.api.events.ProactiveEvent;
import com.github.satr.ask.proactive.api.events.RelevantAudienceType;
import com.github.satr.ask.proactive.api.events.schemas.media.DistributionMethod;
import com.github.satr.ask.proactive.api.events.schemas.media.MediaContentAvailable;
import com.github.satr.ask.proactive.api.events.schemas.media.MediaTypes;
import com.github.satr.common.DateTimeUtil;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Locale;

import static org.junit.Assert.fail;

public final class ObjectMother {

    public static TestClientIdSecretProvider getTestClientIdSecretProvider() {
        TestClientIdSecretProvider clientIdSecretProvider = null;
        try {
            clientIdSecretProvider = new TestClientIdSecretProvider();
        } catch (IOException e) {
            fail("Unable to read file with client id and secret.");
            e.printStackTrace();
        }
        return clientIdSecretProvider;
    }

    public static ProactiveEvent getProactiveEvent(int startTimeNowPlus10MinutesPlusMinutes, int expiryTimeNowPlus10MinutesPlusMinutes) {
        ProactiveEvent proactiveEvent = new ProactiveEvent();
        MediaContentAvailable mediaEvent = new MediaContentAvailable();
        OffsetDateTime nowPlus10minutes = DateTimeUtil.utcNow().plusMinutes(10);
        OffsetDateTime startTime = nowPlus10minutes.plusMinutes(startTimeNowPlus10MinutesPlusMinutes);
        OffsetDateTime expiryTime = nowPlus10minutes.plusMinutes(expiryTimeNowPlus10MinutesPlusMinutes);
        mediaEvent.getAvailability()
                .withProviderFromLocalizedAttribute()//should be same as in localised attributes or from there
                .withMethod(DistributionMethod.AIR)
                .withStartTime(startTime);
        mediaEvent.getContent()
                .withNameFromLocalizedAttribute() //should be same as in localised attributes or from there
                .withContentType(MediaTypes.EPISODE);
        proactiveEvent.setEventWithSchema(mediaEvent);
        proactiveEvent.setExpiryTime(expiryTime);
        proactiveEvent.addLocalizedAttribute(mediaEvent.getLocalizedAttribute(Locale.US)
                .withProviderName("Send Event Provider Example")
                .withContentName("Content example"));
        proactiveEvent.addLocalizedAttributes(mediaEvent.getLocalizedAttributes("Send Event Provider Example", "Other locale Content example", Locale.GERMANY, Locale.CANADA));
        proactiveEvent.getRelevantAudience().setType(RelevantAudienceType.Multicast);

        return proactiveEvent;
    }

}
