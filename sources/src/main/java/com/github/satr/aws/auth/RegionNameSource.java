package com.github.satr.aws.auth;

import com.github.satr.ask.proactive.api.ProactiveEventProvider;

/**
 * The source of a region name of the AWS SecretManager, holding values in corresponding keys "client_id", "client_secret"
 *   <li>{@link RegionNameSource#StringValue} the name of a region like: "US_EAST_1"</li>
 *   <li>{@link RegionNameSource#EnvironmentVariables} the name of an environment variable, holding a name of a region like: "US_EAST_1"</li>
 */
public enum RegionNameSource {
    /**
     *{@link RegionNameSource#EnvironmentVariables} the name of an environment variable, holding a name of a region like: "US_EAST_1"
     */
    EnvironmentVariables,
    /**
     *{@link RegionNameSource#StringValue} the name of a region like: "US_EAST_1"
     */
    StringValue
}
