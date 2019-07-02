package com.github.satr.aws.lambda;
// Copyright © 2019, github.com/satr, MIT License

import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.github.satr.ask.proactive.api.EnvironmentVariable;
import com.github.satr.ask.proactive.api.ProactiveEventProvider;
import com.github.satr.ask.proactive.api.events.ProactiveEvent;
import com.github.satr.ask.proactive.api.net.AskProactiveEventHttpClient;
import com.github.satr.aws.auth.*;
import com.github.satr.aws.regions.InvalidRegionNameException;
import com.github.satr.common.OperationResult;
import com.github.satr.common.net.ApacheHttpClientWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/// set environment variables in a Lambda function configuration dashboard (names and values are described in <ref=EnvironmentVariable>). Default: Dev, NorthEast
public abstract class SendProactiveEventRequestHandler implements RequestHandler<Map<String, String>, String> {
    private final ProactiveEventProvider proactiveEventProvider;
    private AskProactiveEventHttpClient httpClientWrapper;

    public SendProactiveEventRequestHandler(String alexaClientIdSecretAwsSecretName, Regions region, ProactiveEventProvider proactiveEventProvider) throws InvalidRegionNameException {
        this(alexaClientIdSecretAwsSecretName, region.getName(), proactiveEventProvider);
    }

    public SendProactiveEventRequestHandler(String alexaClientIdSecretAwsSecretName, String region, ProactiveEventProvider proactiveEventProvider) throws InvalidRegionNameException {
        this(new ApacheHttpClientWrapper(),
                new AwsSecretsClientIdSecretProvider(alexaClientIdSecretAwsSecretName, region),
                proactiveEventProvider);
    }

    public SendProactiveEventRequestHandler(String alexaSkillClientId, String alexaSkillClientSecret, AlexaSkillClientIdSecretSource clientIdSecretSource, Regions region, ProactiveEventProvider proactiveEventProvider) throws InvalidRegionNameException {
        this(alexaSkillClientId, alexaSkillClientSecret, clientIdSecretSource, region.getName(), proactiveEventProvider);
    }

    public SendProactiveEventRequestHandler(String alexaSkillClientId, String alexaSkillClientSecret, AlexaSkillClientIdSecretSource clientIdSecretSource, String region, ProactiveEventProvider proactiveEventProvider) throws InvalidRegionNameException {
        this(new ApacheHttpClientWrapper(),
                (ClientIdSecretProvider) (clientIdSecretSource == AlexaSkillClientIdSecretSource.EnvironmentVariables
                        ? new EnvironmentVariablesClientIdSecretAwsSecretProvider(alexaSkillClientId, alexaSkillClientSecret)
                        : new BasicClientIdSecretAwsSecretProvider(alexaSkillClientId, alexaSkillClientSecret)),
                proactiveEventProvider);
    }

    public SendProactiveEventRequestHandler(ApacheHttpClientWrapper httpClientWrapper, ClientIdSecretProvider secretProvider, ProactiveEventProvider proactiveEventProvider) {
        this.httpClientWrapper = new AskProactiveEventHttpClient(secretProvider, httpClientWrapper);
        this.proactiveEventProvider = proactiveEventProvider;
    }

    @Override
    public String handleRequest(Map<String, String> input, Context context) {
        LambdaLogger logger = context.getLogger();
        List<ProactiveEvent> events = proactiveEventProvider.getEvents();
        if(events.isEmpty()) {
            String emptyCollectionMessage = "No events to be sent";
            logger.log(emptyCollectionMessage);
            return emptyCollectionMessage;
        }
        int MaxEvents = 10;
        if(events.size() > MaxEvents) {
            String tooManyEvents = String.format("Maximum %d events can be sent at one time. Sending first %d event.", MaxEvents, MaxEvents);
            logger.log(tooManyEvents);
        } else {
            logger.log(String.format("Send %d proactive event(s).", events.size()));
        }
        ArrayList<String> outputMessages = new ArrayList<>();
        int eventCount = 0;
        for (ProactiveEvent event: events) {
            if(eventCount >= MaxEvents)
                break;
            OperationResult result = httpClientWrapper.send(event);
            logResult(logger, result);
            String outputMessage = result.isSuccess()
                    ? String.format("Event #%d has been sent with referenceId: %s (timestamp: %s, expired: %s)", eventCount + 1, event.getReferenceId(), event.getTimestamp(), event.getExpiryTime())
                    : String.format("Event #%d has not been sent. Error: %s", eventCount + 1, result.getErrorsAsString());
            logger.log(outputMessage);
            outputMessages.add(outputMessage);
        }
        return String.join("\n", outputMessages);
    }

    private void logResult(LambdaLogger logger, OperationResult result) {
        String logLevel = EnvironmentVariable.getLogLevel();
        ArrayList<String> log = new ArrayList<>();
        boolean warning = false;
        boolean info = false;
        boolean verbose = false;
        if("verbose".equalsIgnoreCase(logLevel))
            warning = info = verbose = true;
        else if("warning".equalsIgnoreCase(logLevel))
            warning = true;
        else
            warning = info = true;
        for (String message:result.getHistory()) {
            if((message.startsWith("Warning:") && warning)
                || (message.startsWith("Info:") && info)
                || (message.startsWith("Verbose:") && verbose)
                || (message.startsWith("Error:"))) {
                log.add(message);
            }
        }
        logger.log(String.join("\n", log));
    }
}
