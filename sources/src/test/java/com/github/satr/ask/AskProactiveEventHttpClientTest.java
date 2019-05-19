package com.github.satr.ask;

import com.amazonaws.regions.Regions;
import com.github.satr.ask.components.ObjectMother;
import com.github.satr.ask.proactive.api.events.ProactiveEvent;
import com.github.satr.ask.proactive.api.net.AskProactiveEventHttpClient;
import com.github.satr.common.OperationResult;
import com.github.satr.common.net.ApacheHttpClientWrapper;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class AskProactiveEventHttpClientTest {
    @Test
    public void testSendEvent() {
        ApacheHttpClientWrapper httpClient = new ApacheHttpClientWrapper();//.withLoggingToConsole();
        AskProactiveEventHttpClient eventHttpClient = new AskProactiveEventHttpClient(ObjectMother.getTestClientIdSecretProvider(), httpClient);

        ProactiveEvent proactiveEvent = ObjectMother.getProactiveEvent(5);
        OperationResult result = eventHttpClient.send(proactiveEvent);

        System.out.println(result.getHistoryAsString());

        assertTrue(result.isSuccess());
    }

}

