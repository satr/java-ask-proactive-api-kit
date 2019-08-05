package com.github.satr.ask;

import com.github.satr.aws.regions.InvalidRegionNameException;
import com.github.satr.aws.auth.AwsSecretsClientIdSecretProvider;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class ClientIdSecretProviderTest {

    //Create the secret with a name "AlexaNotifier" (or another name, and put it here) in the AWS SecretManager, in the us-east-1 region (N.Virginia)
    //with key-values:
    //'client_id':'<CLIENT_ID_from_Alexa_skill_dashboard_tab_PERMISSIONS>'
    //'client_secret':'<CLIENT_SECRET_from_Alexa_skill_dashboard_tab_PERMISSIONS>'
    @Test
    public void testClient() throws InvalidRegionNameException {
        AwsSecretsClientIdSecretProvider valuePairProvider = new AwsSecretsClientIdSecretProvider("AlexaNotifier", "us-east-1");

        assertNotNull(valuePairProvider.getClientId());
        assertNotNull(valuePairProvider.getClientSecret());
    }
}
