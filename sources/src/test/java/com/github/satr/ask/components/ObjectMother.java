package com.github.satr.ask.components;

import com.github.satr.ask.proactive.api.events.ProactiveEvent;
import com.github.satr.ask.proactive.api.events.RelevantAudienceType;
import com.github.satr.ask.proactive.api.events.schemas.media.DistributionMethod;
import com.github.satr.ask.proactive.api.events.schemas.media.MediaContentAvailable;
import com.github.satr.ask.proactive.api.events.schemas.media.MediaTypes;

import java.io.IOException;
import java.time.LocalDateTime;
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

    public static ProactiveEvent getProactiveEvent(int expiryTimeNowPlusSeconds) {
        ProactiveEvent proactiveEvent = new ProactiveEvent();
        MediaContentAvailable mediaEvent = new MediaContentAvailable();
        mediaEvent.getAvailability()
                .withProviderFromLocalizedAttribute()//should be sa as in localised attributes or from there
                .withMethod(DistributionMethod.AIR)
                .withStartTime(LocalDateTime.now().plusHours(1));
        mediaEvent.getContent()
                .withNameFromLocalizedAttribute() //should be sa as in localised attributes or from there
                .withContentType(MediaTypes.EPISODE);
        proactiveEvent.setEventWithSchema(mediaEvent);
        proactiveEvent.setExpiryTimeNowPlusSeconds(expiryTimeNowPlusSeconds);
        proactiveEvent.addLocalizedAttribute(mediaEvent.getLocalizedAttribute(Locale.US)
                .withProviderName("Send Event Provider Example")
                .withContentName("Content example"));
        proactiveEvent.addLocalizedAttributes(mediaEvent.getLocalizedAttributes("Send Event Provider Example", "Other locale Content example", Locale.GERMANY, Locale.CANADA));
        proactiveEvent.getRelevantAudience().setType(RelevantAudienceType.Multicast);

        return proactiveEvent;
    }
}
