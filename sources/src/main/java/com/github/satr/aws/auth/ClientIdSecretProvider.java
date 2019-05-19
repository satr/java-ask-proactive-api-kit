package com.github.satr.aws.auth;
// Copyright © 2019, github.com/satr, MIT License

public interface ClientIdSecretProvider {
    String getClientId();
    String getClientSecret();
}
