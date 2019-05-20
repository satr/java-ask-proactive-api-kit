package com.github.satr.ask.proactive.api.net.entities;
// Copyright © 2019, github.com/satr, MIT License

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.satr.common.DateTimeUtil;
import java.time.OffsetDateTime;

public class AccessToken {
    private OffsetDateTime expirationDateTime = null;
    @JsonProperty("access_token") private String accessToken;
    @JsonProperty("token_type") private String tokenType;
    @JsonProperty("scope") private String scope;
    @JsonProperty("expires_in") private int expiresIn;

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
        if(expiresIn > 60)
            expiresIn -= 60;//short expiration on one minute
        expirationDateTime = DateTimeUtil.utcNow().plusSeconds(expiresIn);
    }

    public boolean isExpired() {
        OffsetDateTime now = OffsetDateTime.now();
        return isExpired(now);
    }

    //for testing purpose
    public boolean isExpired(OffsetDateTime now) {
        return expirationDateTime.isBefore(now);
    }

    public String expiredAsString() {
        return DateTimeUtil.toIsoString(expirationDateTime);
    }
}
