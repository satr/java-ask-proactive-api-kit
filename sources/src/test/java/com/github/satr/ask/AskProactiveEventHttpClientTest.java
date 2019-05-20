package com.github.satr.ask;

import com.github.satr.ask.components.ObjectMother;
import com.github.satr.ask.proactive.api.events.ProactiveEvent;
import com.github.satr.ask.proactive.api.net.AskProactiveEventHttpClient;
import com.github.satr.common.OperationResult;
import com.github.satr.common.net.ApacheHttpClientWrapper;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertTrue;

public class AskProactiveEventHttpClientTest {
    @Test
    public void testSendEvent() {
        ApacheHttpClientWrapper httpClient = new ApacheHttpClientWrapper();//.withLoggingToConsole();
        AskProactiveEventHttpClient eventHttpClient = new AskProactiveEventHttpClient(ObjectMother.getTestClientIdSecretProvider(), httpClient);

        List<ProactiveEvent> proactiveEvents = ObjectMother.getProactiveEvents(5, 10);
        OperationResult result = eventHttpClient.send(proactiveEvents.get(0));

        System.out.println(result.getHistoryAsString());

        assertTrue(result.isSuccess());
    }

}

