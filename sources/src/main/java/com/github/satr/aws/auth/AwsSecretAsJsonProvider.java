package com.github.satr.aws.auth;
// Copyright © 2019, github.com/satr, MIT License

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.satr.aws.regions.InvalidRegionNameException;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;

public abstract class AwsSecretAsJsonProvider<T> extends AwsSecretProvider {
    public AwsSecretAsJsonProvider(String secretName, String region) throws InvalidRegionNameException {
        super(secretName, region);
    }

    @Override
    protected void processSecret(String secretString) {
        T secret;
        try {
            secret = new ObjectMapper().readValue(secretString, getSecretEntityType());
        } catch (IOException e) {
            throw new SecurityException("Unable to get a Secret.", e);
        }
        processSecret(secret);
    }

    protected abstract Class<T> getSecretEntityType();

    protected abstract void processSecret(T secret);
}
