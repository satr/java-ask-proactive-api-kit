package com.github.satr.ask.proactive.api;
// Copyright © 2019, github.com/satr, MIT License

public final class EnvironmentVariable {
    public static final String ApiEnvironment = "API_ENVIRONMENT";//"Dev" (default), "Prod"
    public static final String ApiRegionGroup = "PROACTIVE_API_REGION_GROUP";//"NorthEast" (default), "Europe", "FarEast"
    public static final String LogLevel = "LOG_LEVEL";//"Warning", "Info" (default), "Verbose"

    public static String getApiEnvironment() {
        return System.getenv(ApiEnvironment);
    }

    public static String getApiRegionGroup() {
        return System.getenv(ApiRegionGroup);
    }

    public static String getLogLevel() {
        return System.getenv(LogLevel);
    }
}
