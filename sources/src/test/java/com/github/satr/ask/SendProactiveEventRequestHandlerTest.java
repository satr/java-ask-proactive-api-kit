package com.github.satr.ask;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.github.satr.ask.components.ObjectMother;
import com.github.satr.ask.components.TestClientIdSecretProvider;
import com.github.satr.ask.components.TestProactiveEventProvider;
import com.github.satr.ask.proactive.api.ProactiveEventProvider;
import com.github.satr.ask.proactive.api.events.ProactiveEvent;
import com.github.satr.aws.auth.AlexaSkillClientIdSecretSource;
import com.github.satr.aws.lambda.SendProactiveEventRequestHandler;
import com.github.satr.aws.regions.InvalidRegionNameException;
import com.github.satr.common.net.ApacheHttpClientWrapper;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SendProactiveEventRequestHandlerTest {
    @Mock
    Context context;
    @Mock
    LambdaLogger loggerMock;
    @Mock
    ProactiveEventProvider proactiveEventProvider;

    private SendProactiveEventRequestHandler requestHandler;
    private List<ProactiveEvent> proactiveEvents;
    private final String clientId = "PUT_YOUR_CLIENT_ID_HERE";
    private final String clientSecret = "PUT_YOUR_CLIENT_SECRET_HERE";

    @Before
    public void setUp() {
        when(context.getLogger()).thenReturn(loggerMock);
        doAnswer(call -> {
            System.out.println((String)call.getArgument(0));
            return null;})
                .when(loggerMock).log(anyString());
    }

    //Create the secret with a name "AlexaNotifier" (or another name, and put it here) in the AWS SecretManager, in the us-east-1 region (N.Virginia)
    //with key-values:
    //'client_id':'<CLIENT_ID_from_Alexa_skill_dashboard>'
    //'client_secret':'<CLIENT_SECRET_from_Alexa_skill_dashboard>'
    @Test
    public void handleRequestWithDefaultCtor() throws InvalidRegionNameException {
        requestHandler = new SendProactiveEventRequestHandler("AlexaNotifier", Regions.US_EAST_1, new TestProactiveEventProvider());

        String respond = requestHandler.handleRequest(null, context);

        System.out.println(respond);
        assertNotNull(respond);
        assertTrue(respond.startsWith("Event "));
        assertTrue(respond.contains(" been sent with referenceId: "));
    }

    @Test
    public void testSecretProviderTest() {
        proactiveEvents = ObjectMother.getProactiveEvents(5, 10);
        when(proactiveEventProvider.getEvents()).thenReturn(proactiveEvents);
        ApacheHttpClientWrapper httpClient = new ApacheHttpClientWrapper();//.withLoggingToConsole();
        TestClientIdSecretProvider secretProvider = ObjectMother.getTestClientIdSecretProvider();

        requestHandler = new SendProactiveEventRequestHandler(httpClient, secretProvider, proactiveEventProvider);

        String respond = requestHandler.handleRequest(null, context);

        System.out.println(respond);
        assertNotNull(respond);
        assertTrue(respond.startsWith("Event "));
        assertTrue(respond.contains(" been sent with referenceId: " + proactiveEvents.get(0).getReferenceId()));
    }

    @Test
    public void testRepeatedRequestTest() {
        proactiveEvents = ObjectMother.getProactiveEvents(5, 10);
        when(proactiveEventProvider.getEvents()).thenReturn(proactiveEvents);
        ApacheHttpClientWrapper httpClient = new ApacheHttpClientWrapper();//.withLoggingToConsole();
        TestClientIdSecretProvider secretProvider = ObjectMother.getTestClientIdSecretProvider();

        requestHandler = new SendProactiveEventRequestHandler(httpClient, secretProvider, proactiveEventProvider);

        String respond = requestHandler.handleRequest(null, context);

        System.out.println(respond);
        assertNotNull(respond);
        assertTrue(respond.startsWith("Event "));
        assertTrue(respond.contains(" been sent with referenceId: " + proactiveEvents.get(0).getReferenceId()));

        proactiveEvents.get(0).setTimestampNew();
        proactiveEvents.get(0).setExpiryTimeNowPlusSeconds(1);
        respond = requestHandler.handleRequest(null, context);

        System.out.println(respond);
        assertNotNull(respond);
        assertTrue(respond.startsWith("Event "));
        assertTrue(respond.contains(" been sent with referenceId: " + proactiveEvents.get(0).getReferenceId()));
    }

    @Test
    @Ignore("Before running the test - Specify your ClientId and ClientSecret values in corresponding fields")
    public void testStringValuesClientIdSecretSourceWithRegion() throws InvalidRegionNameException {
        proactiveEvents = ObjectMother.getProactiveEvents(5, 10);
        when(proactiveEventProvider.getEvents()).thenReturn(proactiveEvents);

        requestHandler = new SendProactiveEventRequestHandler(clientId, clientSecret,
                AlexaSkillClientIdSecretSource.StringValues, proactiveEventProvider);

        String respond = requestHandler.handleRequest(null, context);

        System.out.println(respond);
        assertNotNull(respond);
        assertTrue(respond.startsWith("Event "));
        assertTrue(respond.contains(" been sent with referenceId: " + proactiveEvents.get(0).getReferenceId()));
    }

    @Test
    @Ignore("Before running the test - Specify your ClientId and ClientSecret values in corresponding fields")
    public void testClientIdSecretStringValuesSourceWith() {
        proactiveEvents = ObjectMother.getProactiveEvents(5, 10);
        when(proactiveEventProvider.getEvents()).thenReturn(proactiveEvents);

        requestHandler = new SendProactiveEventRequestHandler(clientId, clientSecret,
                AlexaSkillClientIdSecretSource.StringValues, proactiveEventProvider);

        String respond = requestHandler.handleRequest(null, context);

        System.out.println(respond);
        assertNotNull(respond);
        assertTrue(respond.startsWith("Event "));
        assertTrue(respond.contains(" been sent with referenceId: " + proactiveEvents.get(0).getReferenceId()));
    }

    @Test
    @Ignore("Before running the test - put to the test's run configuration environment variables \"SKILL_CLIENT_ID\" and \"SKILL_CLIENT_SECRET\" with corresponding values")
    public void testClientIdSecretEnvironmentVariablesSource() {
        proactiveEvents = ObjectMother.getProactiveEvents(5, 10);
        when(proactiveEventProvider.getEvents()).thenReturn(proactiveEvents);

        //Put to the test's run configuration environment variables "SKILL_CLIENT_ID" and "SKILL_CLIENT_SECRET" with corresponding values
        requestHandler = new SendProactiveEventRequestHandler("SKILL_CLIENT_ID", "SKILL_CLIENT_SECRET",
                AlexaSkillClientIdSecretSource.EnvironmentVariables, proactiveEventProvider);

        String respond = requestHandler.handleRequest(null, context);

        System.out.println(respond);
        assertNotNull(respond);
        assertTrue(respond.startsWith("Event "));
        assertTrue(respond.contains(" been sent with referenceId: " + proactiveEvents.get(0).getReferenceId()));
    }
}
