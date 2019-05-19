package com.github.satr.ask;

import com.github.satr.aws.regions.InvalidRegionNameException;
import com.github.satr.aws.auth.ClientIdSecretProviderImpl;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class ClientIdSecretProviderTest {
    @Test
    public void testClient() throws InvalidRegionNameException {
        ClientIdSecretProviderImpl valuePairProvider = new ClientIdSecretProviderImpl("AlexaProactiveApiExample", "us-east-1");

        assertNotNull(valuePairProvider.getClientId());
        assertNotNull(valuePairProvider.getClientSecret());
    }
}
