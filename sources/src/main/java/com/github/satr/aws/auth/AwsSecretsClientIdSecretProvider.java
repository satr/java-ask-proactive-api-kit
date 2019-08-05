package com.github.satr.aws.auth;
// Copyright © 2019, github.com/satr, MIT License

import com.github.satr.aws.regions.InvalidRegionNameException;
import com.github.satr.aws.auth.entities.ClientIdSecretPair;

/**
 * Provides a pair of client-id and client-secret string values, stored in the AWS SecretManager,
 * with keys "client_id" and "client_secret".
* */
public class AwsSecretsClientIdSecretProvider extends AwsSecretAsJsonProvider<ClientIdSecretPair> implements ClientIdSecretProvider{
    private String clientId;
    private String clientSecret;

    /**
     * @param secretName      the name of the secret in the AWS SecretManager, with keys "client_id" and "client_secret".
     * @param region          the name of the AWS region, where the secret is created ({@link com.amazonaws.regions.Regions}).
     *                        Example: "us-east-1".
     *
     * @throws                {@link InvalidRegionNameException}, when the {@link @region} contains wrong region name.
    * */
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

    /**
     * Retrieves the client-id value.
     * */
    @Override
    public String getClientId() {
        return clientId;
    }

    /**
     * Retrieves the client-secret value.
     * */
    @Override
    public String getClientSecret() {
        return clientSecret;
    }
}
