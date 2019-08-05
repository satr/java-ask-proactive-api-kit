package com.github.satr.ask.client.mocked;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.github.satr.ask.components.ObjectMother;
import com.github.satr.ask.proactive.api.ProactiveEventProvider;
import com.github.satr.ask.proactive.api.events.ProactiveEvent;
import com.github.satr.ask.proactive.api.net.HttpClientWrapperFactory;
import com.github.satr.aws.auth.AwsSecretsClientIdSecretProviderFactory;
import com.github.satr.aws.auth.ClientIdSecretProvider;
import com.github.satr.aws.lambda.SendProactiveEventRequestHandler;
import com.github.satr.aws.regions.InvalidRegionNameException;
import com.github.satr.common.net.HttpClientAction;
import com.github.satr.common.net.HttpClientWrapper;
import org.junit.Before;
import org.mockito.Mock;

import java.io.IOException;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

public abstract class AbstractMockedSecretsClientIdSecretProviderTestSuite {
    protected SendProactiveEventRequestHandler requestHandler;
    @Mock
    Context context;
    @Mock
    LambdaLogger loggerMock;
    @Mock
    HttpClientWrapper httpClientWrapper;
    @Mock
    ProactiveEventProvider proactiveEventProvider;
    @Mock
    ClientIdSecretProvider clientIdSecretProvider;
    protected String testClientId = ObjectMother.getRandomString();
    protected String testClientSecret = ObjectMother.getRandomString();

    @Before
    public void setUp() throws IOException, InvalidRegionNameException {
        when(context.getLogger()).thenReturn(loggerMock);
        doAnswer(call -> {
            System.out.println((String)call.getArgument(0));
            return null;})
                .when(loggerMock).log(anyString());
        HttpClientWrapperFactory.setClient(httpClientWrapper);
        when(clientIdSecretProvider.getClientId()).thenReturn(testClientId);
        when(clientIdSecretProvider.getClientSecret()).thenReturn(testClientSecret);
        AwsSecretsClientIdSecretProviderFactory.setProvider(clientIdSecretProvider);
        setUpCustom();
    }

    protected abstract void setUpCustom() throws InvalidRegionNameException;
}
