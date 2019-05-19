package com.github.satr.aws.lambda;
// Copyright © 2019, github.com/satr, MIT License

import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.github.satr.ask.proactive.api.ProactiveEventProvider;
import com.github.satr.ask.proactive.api.ProactiveEventProviderImpl;
import com.github.satr.ask.proactive.api.events.ProactiveEvent;
import com.github.satr.ask.proactive.api.net.AskProactiveEventHttpClient;
import com.github.satr.aws.auth.ClientIdSecretProvider;
import com.github.satr.aws.auth.ClientIdSecretProviderImpl;
import com.github.satr.common.OperationResult;
import com.github.satr.common.net.ApacheHttpClientWrapper;

import java.util.Map;

/// set environment variables in a Lambda function configuration dashboard (names and values are described in <ref=EnvironmentVariable>). Default: Dev, NorthEast
public abstract class SendProactiveEventRequestHandler implements RequestHandler<Map<String, String>, String> {
    private final ProactiveEventProvider proactiveEventProvider;
    private AskProactiveEventHttpClient httpClientWrapper;

    public SendProactiveEventRequestHandler(String alexaClientIdSecretAwsSecretName, Regions region, ProactiveEventProvider proactiveEventProvider) {
        this(alexaClientIdSecretAwsSecretName, region.name(), proactiveEventProvider);
    }

    public SendProactiveEventRequestHandler(String alexaClientIdSecretAwsSecretName, String region, ProactiveEventProvider proactiveEventProvider) {
        this(new ApacheHttpClientWrapper(),
                new ClientIdSecretProviderImpl(alexaClientIdSecretAwsSecretName, region),
                proactiveEventProvider);
    }

    public SendProactiveEventRequestHandler(ApacheHttpClientWrapper httpClientWrapper, ClientIdSecretProvider secretProvider, ProactiveEventProvider proactiveEventProvider) {
        this.httpClientWrapper = new AskProactiveEventHttpClient(secretProvider, httpClientWrapper);
        this.proactiveEventProvider = proactiveEventProvider;
    }

    @Override
    public String handleRequest(Map<String, String> input, Context context) {
        LambdaLogger logger = context.getLogger();
        logger.log("Send proactive event");
        ProactiveEvent event = proactiveEventProvider.getEvent();
        OperationResult result = httpClientWrapper.send(event);
        logger.log(result.getHistoryAsString());
        return result.isSuccess()
                ? String.format("Event has been sent with referenceId: %s (timestamp: %s, expired: %s)", event.getReferenceId(), event.getTimestamp(), event.getExpiryTime())
                : String.format("Event has not been sent. Error: %s", result.getErrorsAsString());
    }
}
