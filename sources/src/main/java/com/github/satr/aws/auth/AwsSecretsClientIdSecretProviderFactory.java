package com.github.satr.aws.auth;
// Copyright © 2019, github.com/satr, MIT License

import com.github.satr.aws.regions.InvalidRegionNameException;

public final class AwsSecretsClientIdSecretProviderFactory {
    private static ClientIdSecretProvider provider;

    public static ClientIdSecretProvider getProvider(String alexaClientIdSecretAwsSecretName, String region) throws InvalidRegionNameException {
        return provider != null
                ? provider
                : (provider = new AwsSecretsClientIdSecretProvider(alexaClientIdSecretAwsSecretName, region));
    }

    public static void setProvider(ClientIdSecretProvider clientIdSecretProvider) {
        provider = clientIdSecretProvider;
    }
}
