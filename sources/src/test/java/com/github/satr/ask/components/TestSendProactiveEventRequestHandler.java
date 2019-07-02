package com.github.satr.ask.components;

import com.amazonaws.regions.Regions;
import com.github.satr.ask.proactive.api.ProactiveEventProvider;
import com.github.satr.aws.auth.AlexaSkillClientIdSecretSource;
import com.github.satr.aws.regions.InvalidRegionNameException;
import com.github.satr.aws.auth.ClientIdSecretProvider;
import com.github.satr.aws.lambda.SendProactiveEventRequestHandler;
import com.github.satr.common.net.ApacheHttpClientWrapper;

public class TestSendProactiveEventRequestHandler extends SendProactiveEventRequestHandler {
    public TestSendProactiveEventRequestHandler(String alexaClientIdSecretAwsSecretName, Regions region, ProactiveEventProvider proactiveEventProvider) throws InvalidRegionNameException {
        super(alexaClientIdSecretAwsSecretName, region, proactiveEventProvider);
    }

    public TestSendProactiveEventRequestHandler() throws InvalidRegionNameException {
        super("AlexaNotifier", Regions.US_EAST_1, new TestProactiveEventProvider());
    }

    public TestSendProactiveEventRequestHandler(String alexaClientIdSecretAwsSecretName, String region, ProactiveEventProvider proactiveEventProvider) throws InvalidRegionNameException {
        super(alexaClientIdSecretAwsSecretName, region, proactiveEventProvider);
    }

    public TestSendProactiveEventRequestHandler(ApacheHttpClientWrapper httpClientWrapper, ClientIdSecretProvider secretProvider, ProactiveEventProvider proactiveEventProvider) {
        super(httpClientWrapper, secretProvider, proactiveEventProvider);
    }

    public TestSendProactiveEventRequestHandler(String alexaSkillClientId, String alexaSkillClientSecret, AlexaSkillClientIdSecretSource clientIdSecretSource, Regions region, ProactiveEventProvider proactiveEventProvider) throws InvalidRegionNameException {
        super(alexaSkillClientId, alexaSkillClientSecret, clientIdSecretSource, region, proactiveEventProvider);
    }

    public TestSendProactiveEventRequestHandler(String alexaSkillClientId, String alexaSkillClientSecret, AlexaSkillClientIdSecretSource clientIdSecretSource, String region, ProactiveEventProvider proactiveEventProvider) throws InvalidRegionNameException {
        super(alexaSkillClientId, alexaSkillClientSecret, clientIdSecretSource, region, proactiveEventProvider);
    }
}
