package com.github.satr.ask.proactive.api.net;

import org.junit.Test;

import static org.junit.Assert.*;

public class UrlProviderTest {

    @Test
    public void getApiEndpontWithoutSetEnvironmentVariables() {
        String apiEndpont = UrlProvider.getApiEndpont();

        assertEquals(UrlProvider.DevAction.NorthAmerica, apiEndpont);
    }
}