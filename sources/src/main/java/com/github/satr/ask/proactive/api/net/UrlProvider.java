package com.github.satr.ask.proactive.api.net;
// Copyright © 2019, github.com/satr, MIT License

import com.github.satr.ask.proactive.api.ApiEnvironment;
import com.github.satr.ask.proactive.api.EnvironmentVariable;

public final class UrlProvider {

    public static String getApiEndpont() {
        ApiEnvironment apiEnvironment = getApiEnvironment();
        ApiUrlRegionGroup regionGroup = getApiRegionGroup();
        if(apiEnvironment == ApiEnvironment.Prod) {
            switch(regionGroup) {
                case Europe:
                    return ProdAction.Europe;
                case FarEast:
                    return ProdAction.FarEast;
            }
            return ProdAction.NorthAmerica;
        }
        switch(regionGroup) {
            case Europe:
                return DevAction.Europe;
            case FarEast:
                return DevAction.FarEast;
        }
        return DevAction.NorthAmerica;
    }

    private static ApiUrlRegionGroup getApiRegionGroup() {
        String apiRegionGroup = System.getenv(EnvironmentVariable.ApiRegionGroup);
        if ("Europe".equalsIgnoreCase(apiRegionGroup))
            return ApiUrlRegionGroup.Europe;
        if ("FarEast".equalsIgnoreCase(apiRegionGroup))
            return ApiUrlRegionGroup.FarEast;
        return ApiUrlRegionGroup.NorthAmerica;
    }

    private static ApiEnvironment getApiEnvironment() {
        String apiEnv = System.getenv(EnvironmentVariable.ApiEnvironment);
        return "Prod".equalsIgnoreCase(apiEnv) ? ApiEnvironment.Prod : ApiEnvironment.Dev;
    }

    public static final class Auth {
        public static final String BearerToken = "https://api.amazon.com/auth/o2/token";
    }

    public static final class DevAction {
        public static final String NorthAmerica = "https://api.amazonalexa.com/v1/proactiveEvents/stages/development";
        public static final String Europe = "https://api.eu.amazonalexa.com/v1/proactiveEvents/stages/development";
        public static final String FarEast = "https://api.fe.amazonalexa.com/v1/proactiveEvents/stages/development";
    }

    public static final class ProdAction {
        public static final String NorthAmerica = "https://api.amazonalexa.com/v1/proactiveEvents";
        public static final String Europe = "https://api.eu.amazonalexa.com/v1/proactiveEvents";
        public static final String FarEast = "https://api.fe.amazonalexa.com/v1/proactiveEvents";
    }
}
