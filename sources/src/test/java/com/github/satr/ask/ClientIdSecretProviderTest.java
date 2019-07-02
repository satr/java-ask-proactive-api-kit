package com.github.satr.ask;

import com.github.satr.aws.regions.InvalidRegionNameException;
import com.github.satr.aws.auth.AwsSecretsClientIdSecretProvider;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class ClientIdSecretProviderTest {
    @Test
    public void testClient() throws InvalidRegionNameException {
        AwsSecretsClientIdSecretProvider valuePairProvider = new AwsSecretsClientIdSecretProvider("AlexaProactiveApiExample", "us-east-1");

        assertNotNull(valuePairProvider.getClientId());
        assertNotNull(valuePairProvider.getClientSecret());
    }
}
