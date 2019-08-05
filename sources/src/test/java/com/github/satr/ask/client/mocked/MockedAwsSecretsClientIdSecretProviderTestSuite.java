package com.github.satr.ask.client.mocked;

import com.amazonaws.regions.Regions;
import com.github.satr.ask.components.ObjectMother;
import com.github.satr.ask.proactive.api.net.UrlProvider;
import com.github.satr.aws.lambda.SendProactiveEventRequestHandler;
import com.github.satr.aws.regions.InvalidRegionNameException;
import com.github.satr.common.net.HttpClientAction;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MockedAwsSecretsClientIdSecretProviderTestSuite extends AbstractMockedSecretsClientIdSecretProviderTestSuite {

    private ArgumentCaptor<HttpClientAction> clientActionArgumentCaptor;

    @Override
    protected void setUpCustom() throws InvalidRegionNameException {
        requestHandler = new SendProactiveEventRequestHandler("AlexaNotifier", Regions.US_EAST_1, proactiveEventProvider);
        clientActionArgumentCaptor = ArgumentCaptor.forClass(HttpClientAction.class);
    }

    @Test
    public void testBearerTokenRequestProperties() throws InvalidRegionNameException, IOException {
        when(proactiveEventProvider.getEvents())
                .thenReturn(ObjectMother.getProactiveEvents(5, 10));

        requestHandler.handleRequest(null, context);
        Mockito.verify(httpClientWrapper).executeRequest(clientActionArgumentCaptor.capture());

        assertEquals(1, clientActionArgumentCaptor.getAllValues().size());
        HttpClientAction httpClientAction = clientActionArgumentCaptor.getValue();
        HttpUriRequest httpRequest = httpClientAction.getHttpRequest();
        assertEquals(UrlProvider.Auth.BearerToken, httpRequest.getURI());
        assertEquals(HttpPost.class, httpRequest.getClass());
        String requestContentType = ((HttpPost) httpRequest).getEntity().getContentType().getValue();
        assertEquals("application/x-www-form-urlencoded", requestContentType);
    }

    @Test
    public void testBearerTokenProcessRequest() throws InvalidRegionNameException, IOException {
        when(proactiveEventProvider.getEvents())
                .thenReturn(ObjectMother.getProactiveEvents(5, 10));

        requestHandler.handleRequest(null, context);
        Mockito.verify(httpClientWrapper).executeRequest(clientActionArgumentCaptor.capture());

        assertEquals(1, clientActionArgumentCaptor.getAllValues().size());
        HttpClientAction httpClientAction = clientActionArgumentCaptor.getValue();
        HttpUriRequest httpRequest = httpClientAction.getHttpRequest();
        assertEquals(UrlProvider.Auth.BearerToken, httpRequest.getURI());
        assertEquals(HttpPost.class, httpRequest.getClass());
        String requestContentType = ((HttpPost) httpRequest).getEntity().getContentType().getValue();
        assertEquals("application/x-www-form-urlencoded", requestContentType);
    }
}
