package com.github.satr.ask;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.satr.ask.components.ObjectMother;
import com.github.satr.ask.proactive.api.events.ProactiveEvent;
import com.github.satr.ask.proactive.api.net.AskProactiveEventHttpClient;
import com.github.satr.ask.proactive.api.net.entities.AccessToken;
import com.github.satr.ask.proactive.api.net.entities.ErrorRespond;
import com.github.satr.common.OperationResult;
import com.github.satr.common.net.ApacheHttpClientWrapper;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.regex.Pattern;

import static org.junit.Assert.*;

public class HttpRespondBodyTest {
    @Test
    public void testMapBearerTokenRespond() {
        String text = "{\"access_token\":\"Atc|00000000000000000mHsYGQAnWyEt3bk_aA\",\"scope\":\"alexa::proactive_events\",\"token_type\":\"bearer\",\"expires_in\":3600}";
        try {
            AccessToken accessToken = new ObjectMapper().disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES).readValue(text, AccessToken.class);
            assertNotNull(accessToken);
            assertEquals(accessToken.getAccessToken(), "Atc|00000000000000000mHsYGQAnWyEt3bk_aA");
            assertEquals(accessToken.getScope(), "alexa::proactive_events");
            assertEquals(accessToken.getTokenType(), "bearer");
            assertEquals(accessToken.getExpiresIn(), 3600);
            assertFalse(accessToken.isExpired());
        } catch (IOException e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }


    @Test
    public void testBearerTokenExpiredAfterOneSecond() {
        String text = "{\"access_token\":\"Atc|00000000000000000mHsYGQAnWyEt3bk_aA\",\"scope\":\"alexa::proactive_events\",\"token_type\":\"bearer\",\"expires_in\":3600}";
        try {
            AccessToken accessToken = new ObjectMapper().disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES).readValue(text, AccessToken.class);
            assertEquals(accessToken.getExpiresIn(), 3600);
            assertTrue(accessToken.isExpired(LocalDateTime.now().plusSeconds(3600)));
            assertNotNull(accessToken.expiredAsString());
        } catch (IOException e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    @Test
    public void testBearerTokenRespondHasExpectedFields() {
        Pattern pattern = Pattern.compile(".*(\"access_token\").+(\"scope\").+(\"token_type\").+(\"expires_in\").*");
        String input = "{\"access_token\":\"Atc|00000000000000P3BV3NKW8C43xIL9GFnWyEt3bk_aA\",\"scope\":\"alexa::proactive_events\",\"token_type\":\"bearer\",\"expires_in\":3600}";
        assertTrue(pattern.matcher(input).matches());
    }

    @Test
    public void testExpectedFieldInRespond() {
        String input = "{\"access_token\":\"Atc|00000000000000P3BV3NKW8C43xIL9GFnWyEt3bk_aA\",\"scope\":\"alexa::proactive_events\",\"token_type\":\"bearer\",\"expires_in\":3600}";
        assertTrue(input.contains("\"access_token\"") && input.contains("\"scope\"") && input.contains("\"token_type\"") && input.contains("\"expires_in\""));
    }
    @Test
    public void testMapBearerTokenFailedRespond() {
        String text = "{\"error_index\":\"7l8OuXNldD3V7gE0MmuBjV8y1aNgFLEg==\",\"error_description\":\"Malformed request\",\"error\":\"invalid_request\"}";
        try {
            ErrorRespond respond = new ObjectMapper().disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                                        .readValue(text, ErrorRespond.class);
            assertNotNull(respond);
            assertEquals(respond.getError(), "invalid_request");
            assertEquals(respond.getErrorDescription(), "Malformed request");
            assertNotNull(respond.createdOnAsString());
        } catch (IOException e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    @Test
    public void testSuspectedFailedRespond() {
        String input = "{\"error_index\":\"7l8OuXNldD3V7gE0MmuBjV8y1aNgFLEg==\",\"error_description\":\"Malformed request\",\"error\":\"invalid_request\"}";
        assertTrue(input.contains("\"error_index\"") && input.contains("\"error_description\"") && input.contains("\"error\""));
    }

    @Test
    public void testRespondMessage() {
        String input = "{\\n\"type\": \"Bad Request\", \"message\": \"Violations for metadata: [expiryTime should be at least 5 minutes in the future and no more than 24 hours after the current time]\"\\n}";
        assertTrue(input.contains("\"type\"") && input.contains("\"Bad Request\"")
                && input.contains("\"message\"") && input.contains("Violations for metadata") && input.contains("expiryTime"));
    }
}

