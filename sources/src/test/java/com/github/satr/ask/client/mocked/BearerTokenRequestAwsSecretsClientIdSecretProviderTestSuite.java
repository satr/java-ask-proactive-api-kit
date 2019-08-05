package com.github.satr.ask.client.mocked;

import com.amazonaws.regions.Regions;
import com.github.satr.ask.components.ObjectMother;
import com.github.satr.ask.proactive.api.net.UrlProvider;
import com.github.satr.aws.lambda.SendProactiveEventRequestHandler;
import com.github.satr.aws.regions.InvalidRegionNameException;
import com.github.satr.common.net.HttpClientAction;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URLEncodedUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BearerTokenRequestAwsSecretsClientIdSecretProviderTestSuite extends AbstractMockedSecretsClientIdSecretProviderTestSuite {
    private ArgumentCaptor<HttpClientAction> clientActionArgumentCaptor;
    private static final String VALID_ACCESS_TOKEN_REQUEST_CONTENT = "{\"access_token\":\"Atc|00000000000000000mHsYGQAnWyEt3bk_aA\",\"scope\":\"alexa::proactive_events\",\"token_type\":\"bearer\",\"expires_in\":3600}";

    @Override
    protected void setUpCustom() throws InvalidRegionNameException {
        requestHandler = new SendProactiveEventRequestHandler("AlexaNotifier", Regions.US_EAST_1, proactiveEventProvider);
        clientActionArgumentCaptor = ArgumentCaptor.forClass(HttpClientAction.class);
        when(proactiveEventProvider.getEvents())
                .thenReturn(ObjectMother.getProactiveEvents(5, 10));
    }

    @Test
    public void testExecuteRequestHasOneArgument() throws InvalidRegionNameException, IOException {
        requestHandler.handleRequest(null, context);
        Mockito.verify(httpClientWrapper).executeRequest(clientActionArgumentCaptor.capture());

        assertEquals(1, clientActionArgumentCaptor.getAllValues().size());
    }

    @Test
    public void testUriIsAuth() throws InvalidRegionNameException, IOException {
        requestHandler.handleRequest(null, context);
        Mockito.verify(httpClientWrapper).executeRequest(clientActionArgumentCaptor.capture());

        HttpClientAction httpClientAction = clientActionArgumentCaptor.getValue();
        HttpUriRequest httpRequest = httpClientAction.getHttpRequest();
        assertEquals(UrlProvider.Auth.BearerToken, httpRequest.getURI().toString());
    }

    @Test
    public void testRequestIsPostMethod() throws InvalidRegionNameException, IOException {
        requestHandler.handleRequest(null, context);
        Mockito.verify(httpClientWrapper).executeRequest(clientActionArgumentCaptor.capture());

        HttpClientAction httpClientAction = clientActionArgumentCaptor.getValue();
        HttpUriRequest httpRequest = httpClientAction.getHttpRequest();
        assertEquals(HttpPost.class, httpRequest.getClass());
        assertEquals("POST", httpRequest.getMethod());
    }

    @Test
    public void testRequestContentType() throws InvalidRegionNameException, IOException {
        requestHandler.handleRequest(null, context);
        Mockito.verify(httpClientWrapper).executeRequest(clientActionArgumentCaptor.capture());

        HttpClientAction httpClientAction = clientActionArgumentCaptor.getValue();
        HttpUriRequest httpRequest = httpClientAction.getHttpRequest();
        HttpEntity entity = ((HttpPost) httpRequest).getEntity();
        String requestContentType = entity.getContentType().getValue();
        assertEquals("application/x-www-form-urlencoded", requestContentType);
    }

    @Test
    public void testRequestEntityHasSecretId() throws InvalidRegionNameException, IOException {
        requestHandler.handleRequest(null, context);
        Mockito.verify(httpClientWrapper).executeRequest(clientActionArgumentCaptor.capture());

        HttpClientAction httpClientAction = clientActionArgumentCaptor.getValue();
        HttpUriRequest httpRequest = httpClientAction.getHttpRequest();
        List<NameValuePair> entityPairs = URLEncodedUtils.parse(((HttpPost) httpRequest).getEntity());
        assertTrue(entityPairs.stream().anyMatch(p1 -> p1.getName().equals("grant_type") && p1.getValue().equals("client_credentials")));
        assertTrue(entityPairs.stream().anyMatch(p -> p.getName().equals("scope") && p.getValue().equals("alexa::proactive_events")));
        assertTrue(entityPairs.stream().anyMatch(p -> p.getName().equals("client_id") && p.getValue().equals(testClientId)));
        assertTrue(entityPairs.stream().anyMatch(p -> p.getName().equals("client_secret") && p.getValue().equals(testClientSecret)));
    }

    @Test
    public void test2() throws IOException {
        doAnswer(call -> {
            call.getArgument(0, HttpClientAction.class).processRespond(200, VALID_ACCESS_TOKEN_REQUEST_CONTENT);
            return null;
        }).when(httpClientWrapper).executeRequest(any(HttpClientAction.class));
        String requestMessages = requestHandler.handleRequest(null, context);
        assertEquals("Event #1 has been sent with referenceId:", requestMessages.substring(0, 40));
    }

    @Test
    public void testFailsWithCode400() throws IOException {
        String errorRespond = ObjectMother.getErrorRespond("SomeErrorDescription","SomeError");
        doAnswer(call -> {
            call.getArgument(0, HttpClientAction.class).processRespond(400, errorRespond);
            return null;
        }).when(httpClientWrapper).executeRequest(any(HttpClientAction.class));
        String requestMessages = requestHandler.handleRequest(null, context);
        assertTrue(requestMessages.contains("Event #1 has not been sent. Error: SomeError, SomeErrorDescription"));
    }
}
