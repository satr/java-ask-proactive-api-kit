package com.github.satr.aws.auth;
// Copyright © 2019, github.com/satr, MIT License

import com.amazonaws.services.lambda.model.ResourceNotFoundException;
import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.amazonaws.services.secretsmanager.AWSSecretsManagerClientBuilder;
import com.amazonaws.services.secretsmanager.model.*;
import com.github.satr.aws.regions.InvalidRegionNameException;
import com.github.satr.aws.regions.RegionsValidator;

import java.security.InvalidParameterException;
import java.util.Base64;

public abstract class AwsSecretProvider {

    private final AWSSecretsManager client;

    //This code is based on the example, provided during registering secret in AWS Secrets Manager
    public AwsSecretProvider(String secretName, String region) throws InvalidRegionNameException {
        client  = AWSSecretsManagerClientBuilder.standard()
                .withRegion(RegionsValidator.getValidated(region))
                .build();
        readSecret(secretName);

    }

    private void readSecret(String secretName) {
        // In this sample we only handle the specific exceptions for the 'GetSecretValue' API.
        // See https://docs.aws.amazon.com/secretsmanager/latest/apireference/API_GetSecretValue.html
        // We rethrow the exception by default.

        GetSecretValueRequest getSecretValueRequest = new GetSecretValueRequest()
                .withSecretId(secretName);
        GetSecretValueResult getSecretValueResult = null;

        try {
            getSecretValueResult = client.getSecretValue(getSecretValueRequest);
        } catch (DecryptionFailureException e) {
            // Secrets Manager can't decrypt the protected secret text using the provided KMS key.
            // Deal with the exception here, and/or rethrow at your discretion.
            throw e;
        } catch (InternalServiceErrorException e) {
            // An error occurred on the server side.
            // Deal with the exception here, and/or rethrow at your discretion.
            throw e;
        } catch (InvalidParameterException e) {
            // You provided an invalid value for a parameter.
            // Deal with the exception here, and/or rethrow at your discretion.
            throw e;
        } catch (InvalidRequestException e) {
            // You provided a parameter value that is not valid for the current state of the resource.
            // Deal with the exception here, and/or rethrow at your discretion.
            throw e;
        } catch (ResourceNotFoundException e) {
            // We can't find the resource that you asked for.
            // Deal with the exception here, and/or rethrow at your discretion.
            throw e;
        }

        // Decrypts secret using the associated KMS CMK.
        // Depending on whether the secret is a string or binary, one of these fields will be populated.
        String secretString = getSecretValueResult.getSecretString();
        if(secretString == null) {
            secretString = new String(Base64.getDecoder().decode(getSecretValueResult.getSecretBinary()).array());
        }
        processSecret(secretString);
    }

    protected abstract void processSecret(String secretString);
}
