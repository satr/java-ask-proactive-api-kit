package com.github.satr.ask.proactive.api.net;
// Copyright © 2019, github.com/satr, MIT License

import com.github.satr.common.net.ApacheHttpClientWrapper;
import com.github.satr.common.net.HttpClientWrapper;
/**
 * Provides a http-client wrapper.
 */
public final class HttpClientWrapperFactory {

    private static HttpClientWrapper client;

    /**
     * Retrieves {$link HttpClientWrapper}.
     *
     * Default {@link ApacheHttpClientWrapper} is retrieved,
     * if alternative wrapper-client is not setProvider
     * in the {@link HttpClientWrapperFactory#setClient} method
     */
    public static HttpClientWrapper getClient() {
        return client != null ? client: (client = new ApacheHttpClientWrapper());
    }

    /**
     * Set an alternative http-client wrapper. Primarily for unit-testing purpose.
     */
    public static void setClient(HttpClientWrapper externalClient) {
        client = externalClient;
    }
}
