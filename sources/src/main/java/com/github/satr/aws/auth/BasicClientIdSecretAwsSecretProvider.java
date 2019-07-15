package com.github.satr.aws.auth;
// Copyright © 2019, github.com/satr, MIT License

public class BasicClientIdSecretAwsSecretProvider implements ClientIdSecretProvider {
    private final String clientId;
    private final String clientSecret;

    public BasicClientIdSecretAwsSecretProvider(String clientId, String clientSecret) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    @Override
    public String getClientId() {
        return clientId;
    }

    @Override
    public String getClientSecret() {
        return clientSecret;
    }
}
