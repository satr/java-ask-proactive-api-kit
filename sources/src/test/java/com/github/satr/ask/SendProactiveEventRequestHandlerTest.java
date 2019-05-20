package com.github.satr.ask;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.github.satr.ask.components.ObjectMother;
import com.github.satr.ask.components.TestProactiveEventProvider;
import com.github.satr.ask.components.TestSendProactiveEventRequestHandler;
import com.github.satr.ask.components.TestClientIdSecretProvider;
import com.github.satr.ask.proactive.api.ProactiveEventProvider;
import com.github.satr.ask.proactive.api.events.ProactiveEvent;
import com.github.satr.aws.regions.InvalidRegionNameException;
import com.github.satr.aws.lambda.SendProactiveEventRequestHandler;
import com.github.satr.common.net.ApacheHttpClientWrapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SendProactiveEventRequestHandlerTest {
    @Mock
    Context context;
    @Mock
    LambdaLogger loggerMock;
    @Mock
    ProactiveEventProvider proactiveEventProvider;

    private SendProactiveEventRequestHandler requestHandler;
    private ProactiveEvent proactiveEvent;

    @Before
    public void setUp() throws Exception {
        when(context.getLogger()).thenReturn(loggerMock);
        doAnswer(call -> {
            System.out.println((String)call.getArgument(0));
            return null;})
                .when(loggerMock).log(anyString());
    }

    @Test
    public void handleRequestWithDefaultCtor() throws InvalidRegionNameException {
        requestHandler = new TestSendProactiveEventRequestHandler();

        String respond = requestHandler.handleRequest(null, context);

        System.out.println(respond);
        assertNotNull(respond);
        assertTrue(respond.startsWith("Event has been sent with referenceId"));
    }


    @Test
    public void handleRequest() {
        proactiveEvent = ObjectMother.getProactiveEvent(5, 10);
        when(proactiveEventProvider.getEvent()).thenReturn(proactiveEvent);
        ApacheHttpClientWrapper httpClient = new ApacheHttpClientWrapper();//.withLoggingToConsole();
        TestClientIdSecretProvider secretProvider = ObjectMother.getTestClientIdSecretProvider();
        requestHandler = new TestSendProactiveEventRequestHandler(httpClient, secretProvider, proactiveEventProvider);

        String respond = requestHandler.handleRequest(null, context);

        System.out.println(respond);
        assertNotNull(respond);
        assertTrue(respond.startsWith("Event has been sent with referenceId"));
    }

    @Test
    public void handleRepeatedRequest() throws InvalidRegionNameException {
        proactiveEvent = ObjectMother.getProactiveEvent(5, 10);
        when(proactiveEventProvider.getEvent()).thenReturn(proactiveEvent);
        ApacheHttpClientWrapper httpClient = new ApacheHttpClientWrapper();//.withLoggingToConsole();
        TestClientIdSecretProvider secretProvider = ObjectMother.getTestClientIdSecretProvider();
        requestHandler = new TestSendProactiveEventRequestHandler(httpClient, secretProvider, proactiveEventProvider);

        String respond = requestHandler.handleRequest(null, context);

        System.out.println(respond);
        assertNotNull(respond);
        assertTrue(respond.startsWith("Event has been sent with referenceId"));

        proactiveEvent.setTimestampNew();
        proactiveEvent.setExpiryTimeNowPlusSeconds(1);
        respond = requestHandler.handleRequest(null, context);

        System.out.println(respond);
        assertNotNull(respond);
        assertTrue(respond.startsWith("Event has been sent with referenceId"));
    }
}