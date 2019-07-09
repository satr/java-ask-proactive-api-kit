package com.github.satr.ask.proactive.api;
// Copyright © 2019, github.com/satr, MIT License

public final class EnvironmentVariable {
    public static final String ApiEnvironment = "API_ENVIRONMENT";//"Dev" (default), "Prod"
    public static final String ApiRegionGroup = "PROACTIVE_API_REGION_GROUP";//"NorthEast" (default), "Europe", "FarEast"
    public static final String LogLevel = "LOG_LEVEL";//"Warning", "Info" (default), "Verbose"

    public static String getApiEnvironment() {
        return get(ApiEnvironment);
    }

    public static String getApiRegionGroup() {
        return get(ApiRegionGroup);
    }

    public static String getLogLevel() {
        return get(LogLevel);
    }

    public static String get(String variableName) {
        return System.getenv(variableName);
    }
}
