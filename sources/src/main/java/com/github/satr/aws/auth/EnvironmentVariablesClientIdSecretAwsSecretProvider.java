package com.github.satr.aws.auth;
// Copyright © 2019, github.com/satr, MIT License

public class EnvironmentVariablesClientIdSecretAwsSecretProvider implements ClientIdSecretProvider {
    private final String clientId;
    private final String clientSecret;

    public EnvironmentVariablesClientIdSecretAwsSecretProvider(String clientIdVariableName, String clientSecretVariableName) {
        this.clientId = System.getenv(clientIdVariableName);
        this.clientSecret = System.getenv(clientSecretVariableName);
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
