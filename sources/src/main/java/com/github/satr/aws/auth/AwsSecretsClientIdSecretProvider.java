package com.github.satr.aws.auth;
// Copyright © 2019, github.com/satr, MIT License

import com.github.satr.aws.regions.InvalidRegionNameException;
import com.github.satr.aws.auth.entities.ClientIdSecretPair;

public class AwsSecretsClientIdSecretProvider extends AwsSecretAsJsonProvider<ClientIdSecretPair> implements ClientIdSecretProvider{
    private String clientId;
    private String clientSecret;

    public AwsSecretsClientIdSecretProvider(String secretName, String region) throws InvalidRegionNameException {
        super(secretName, region);
    }

    @Override
    protected Class<ClientIdSecretPair> getSecretEntityType() {
        return ClientIdSecretPair.class;
    }

    @Override
    protected void processSecret(ClientIdSecretPair secret) {
        clientId = secret.getClientId();
        clientSecret = secret.getClientSecret();
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
