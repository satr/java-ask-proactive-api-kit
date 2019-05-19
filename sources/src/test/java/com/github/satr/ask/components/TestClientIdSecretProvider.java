package com.github.satr.ask.components;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.satr.aws.auth.ClientIdSecretProvider;
import com.github.satr.aws.auth.entities.ClientIdSecretPair;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class TestClientIdSecretProvider implements ClientIdSecretProvider {
    private String clientId;
    private String clientSecret;
    String jsonFilePathWithSecret = System.getProperty("user.home") + "/dev/private/aws/alexa-proactive-api-example-secret.json";
/* Content of alexa-proactive-api-example-secret.json
 * client_id and client_secret are taken from a Alexa Skill (in the PERMISSIONS section)
{
  "client_id" : "amzn1.application-oa2-client.c70000000",
  "client_secret" : "01e8000000"
}
*/
    public TestClientIdSecretProvider() throws IOException {
        File file = new File(jsonFilePathWithSecret);
        if(!file.exists()) {
            throw new FileNotFoundException("Json-file with secret not found: " + jsonFilePathWithSecret);
        }
        ClientIdSecretPair secretPair = new ObjectMapper().readValue(file, ClientIdSecretPair.class);
        clientId = secretPair.getClientId();
        clientSecret = secretPair.getClientSecret();
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
