package com.github.satr.aws.auth;
// Copyright © 2019, github.com/satr, MIT License

/**
 * The source of a region name of the AWS SecretManager, holding values in corresponding keys "client_id", "client_secret"
 *   <li>{@link RegionNameSource#StringValue} the name of a region. Example: "us-east-1"</li>
 *   <li>{@link RegionNameSource#EnvironmentVariables} the name of an environment variable, holding a name of a region like: "US_EAST_1"</li>
 */
public enum RegionNameSource {
    /**
     *{@link RegionNameSource#EnvironmentVariables} the name of an environment variable, holding a name of a region like: "US_EAST_1"
     */
    EnvironmentVariables,
    /**
     *{@link RegionNameSource#StringValue} the name of a region. Example: "us-east-1"
     */
    StringValue
}
