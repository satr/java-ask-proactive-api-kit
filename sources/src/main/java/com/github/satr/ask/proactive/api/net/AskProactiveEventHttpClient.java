package com.github.satr.ask.proactive.api.net;
// Copyright © 2019, github.com/satr, MIT License

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.satr.ask.proactive.api.events.ProactiveEvent;
import com.github.satr.ask.proactive.api.net.entities.AccessToken;
import com.github.satr.ask.proactive.api.net.entities.ErrorRespond;
import com.github.satr.aws.auth.ClientIdSecretProvider;
import com.github.satr.common.OperationResult;
import com.github.satr.common.OperationValueResult;
import com.github.satr.common.OperationValueResultImpl;
import com.github.satr.common.net.HttpClientAction;
import com.github.satr.common.net.HttpClientWrapper;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import static org.apache.http.util.TextUtils.isEmpty;

public class AskProactiveEventHttpClient {
    private final HttpClientWrapper httpClientWrapper;
    private final String authEndpoint;
    private HttpPost bearerTokenRequest;
    private AccessToken accessToken;
    private String apiEndpoint;
    private ClientIdSecretProvider clientIdSecretProvider;
    private HttpPost eventActionRequest;

    public AskProactiveEventHttpClient(ClientIdSecretProvider clientIdSecretProvider, HttpClientWrapper httpClientWrapper) {
        this.clientIdSecretProvider = clientIdSecretProvider;
        authEndpoint = UrlProvider.Auth.BearerToken;
        apiEndpoint = UrlProvider.getApiEndpont();
        this.httpClientWrapper = httpClientWrapper;
    }

    public OperationResult send(ProactiveEvent proactiveEvent) {
        OperationValueResult<HttpOperationResult> result = new OperationValueResultImpl<>();
        try {
            int allowedRetryCount = 3;
            do {
                allowedRetryCount--;

                result.resetSuccessState();
                result.setValue(HttpOperationResult.Undefined);

                invalidateBearerToken(result);
                if (result.isFailed() || result.getValue() != HttpOperationResult.Success)
                    return result;

                sendRequest(proactiveEvent, result);
            }
            while((result.getValue() == HttpOperationResult.NeedNewBearerToken) && (allowedRetryCount >= 0));
        } catch (IOException e) {
            e.printStackTrace();
            result.addError(e);
        }
        return result;
    }

    private void invalidateBearerToken(OperationValueResult<HttpOperationResult> result) throws IOException {
        if(accessToken == null || accessToken.isExpired())
            requestBearerToken(result);
    }

    private void requestBearerToken(OperationValueResult<HttpOperationResult> result) throws IOException {
        result.addVerbose("Requesting a new Bearer Token");
        httpClientWrapper.executeRequest(new HttpClientAction() {
            @Override
            public HttpUriRequest getHttpRequest() throws IOException {
                return getBearerTokenRequest();
            }
            @Override
            public void processRespond(int statusCode, String respondBody) throws IOException {
                trySetupBearerToken(statusCode, respondBody, result);
            }
        });
    }

    private void sendRequest(ProactiveEvent proactiveEvent, OperationValueResult<HttpOperationResult> result) throws IOException {
        result.addVerbose("Sending an action request");
        httpClientWrapper.executeRequest(new HttpClientAction() {
            @Override
            public HttpUriRequest getHttpRequest() throws IOException {
                return getActionRequest(proactiveEvent, result);
            }
            @Override
            public void processRespond(int statusCode, String respondBody) throws IOException {
                processSendActionRespond(statusCode, respondBody, result);
            }
        });
    }

    private HttpEntity getActionEntity(ProactiveEvent proactiveEvent, OperationResult result) throws JsonProcessingException, UnsupportedEncodingException {
        String jsonString = new ObjectMapper().writeValueAsString(proactiveEvent);
        result.addVerbose("[Request body]: " + jsonString);
        return new StringEntity(jsonString);
    }

    private UrlEncodedFormEntity getFormParams(String alexaSkillClientId, String alexaSkillClientSecret) throws UnsupportedEncodingException {
        List<NameValuePair> parameters = new ArrayList<>();
        parameters.add(new BasicNameValuePair("grant_type", "client_credentials"));
        parameters.add(new BasicNameValuePair("client_id", alexaSkillClientId));
        parameters.add(new BasicNameValuePair("client_secret", alexaSkillClientSecret));
        parameters.add(new BasicNameValuePair("scope", "alexa::proactive_events"));
        return new UrlEncodedFormEntity(parameters);
    }

    private HttpPost getBearerTokenRequest() throws UnsupportedEncodingException {
        if (bearerTokenRequest != null)
            return bearerTokenRequest;
        bearerTokenRequest = new HttpPost(authEndpoint);
        bearerTokenRequest.setEntity(getFormParams(clientIdSecretProvider.getClientId(), clientIdSecretProvider.getClientSecret()));
        return bearerTokenRequest;
    }

    private HttpUriRequest getActionRequest(ProactiveEvent proactiveEvent, OperationResult result) throws JsonProcessingException, UnsupportedEncodingException {
        if(eventActionRequest != null)
            return eventActionRequest;
        eventActionRequest = new HttpPost(apiEndpoint);
        eventActionRequest.addHeader("Content-Type", "application/json");
        eventActionRequest.addHeader("Authorization", String.format("Bearer %s", accessToken.getAccessToken()));
        eventActionRequest.setEntity(getActionEntity(proactiveEvent, result));
        return eventActionRequest;
    }

    private void trySetupBearerToken(int statusCode, String respondBody, OperationValueResult<HttpOperationResult> result) throws IOException {
        result.addVerbose("Response code: %d", statusCode);
        result.addVerbose("Response body length: %d", isEmpty(respondBody) ? 0 : respondBody.length());

        if (statusCode >= 200 && statusCode < 300) {
            if(!isBearerTokenContent(respondBody)) {
                result.addError("Invalid format of the Bearer Token");
                result.addError("Failure response body:\n%s", respondBody);
                result.setValue(HttpOperationResult.Failed);
                return;
            }
            accessToken = getBearerToken(respondBody);
            result.addVerbose("The new Bearer Token expires: %s", accessToken.expiredAsString());
            result.setValue(HttpOperationResult.Success);
            return;
        }
        if(statusCode < 400 || statusCode >= 500) {
            result.addError(String.format("Unexpected response code %d", statusCode));
        } else {
            ErrorRespond errorRespond = getErrorRespond(respondBody);
            if(errorRespond != null)
                result.addError("Error: %s, %s", errorRespond.getError(), errorRespond.getErrorDescription());
            else
                result.addError("Failed request.");
        }
        result.addError("Failure response body:\n%s", respondBody);
        result.setValue(HttpOperationResult.Failed);
    }

    private void processSendActionRespond(int statusCode, String respondBody, OperationValueResult<HttpOperationResult> result) throws IOException {
        result.addVerbose("Response code: %d", statusCode);
        result.addVerbose("Response body length: %d", isEmpty(respondBody) ? 0 : respondBody.length());

        if (statusCode >= 200 && statusCode < 300) {
            result.addInfo("Request has been successfully sent.");
            result.setValue(HttpOperationResult.Success);
            return;
        }
        if(statusCode < 400 || statusCode >= 500) {
            result.addError(String.format("Unexpected response code %d", statusCode));
            result.setValue(HttpOperationResult.Failed);
        } else if(isBearerTokenExpiredFailureMessage(respondBody)) {
            result.addInfo("Bearer Toked has been expired. Requesting a new one.");
            result.setValue(HttpOperationResult.NeedNewBearerToken);
            clearAccessToken();
        } else if(isBearerTokenInvalidFailureMessage(respondBody)) {
            result.addError("Bearer Toked is invalid. Requesting a new one.");
            result.setValue(HttpOperationResult.NeedNewBearerToken);
            clearAccessToken();
        } else {
            ErrorRespond errorRespond = getErrorRespond(respondBody);
            if(errorRespond != null)
                result.addError("Error: %s, %s", errorRespond.getError(), errorRespond.getErrorDescription());
            else
                result.addError("Failed request.");
            result.setValue(HttpOperationResult.Failed);
        }
        result.addError("Failure response body:\n%s", respondBody);
    }

    private void clearAccessToken() {
        accessToken = null;
    }

    private ErrorRespond getErrorRespond(String respondBody) throws IOException {
        return isErrorRespond(respondBody) ? new ObjectMapper().readValue(respondBody, ErrorRespond.class) : null;
    }

    private boolean isBearerTokenExpiredFailureMessage(String input) {
        return input.contains("\"type\"") && input.contains("\"Bad Request\"") && input.contains("\"message\"")
                && input.contains("Violations for metadata") && input.contains("expiryTime");
    }

    private boolean isBearerTokenInvalidFailureMessage(String input) {
        return input.contains("\"type\"") && input.contains("\"Forbidden\"") && input.contains("\"message\"")
                && input.contains("Token in the request is not valid");
    }

    private boolean isErrorRespond(String input) {
        return input.contains("\"error_description\"") && input.contains("\"error\"");
    }

    private AccessToken getBearerToken(String respondBody) throws IOException {
        return new ObjectMapper().readValue(respondBody, AccessToken.class);
    }

    private boolean isBearerTokenContent(String input) {
        return !isEmpty(input) && input.contains("\"access_token\"") && input.contains("\"scope\"")
                && input.contains("\"token_type\"") && input.contains("\"expires_in\"");
    }

    private enum HttpOperationResult {
        Undefined,
        Success,
        Failed,
        NeedNewBearerToken
    }
}

