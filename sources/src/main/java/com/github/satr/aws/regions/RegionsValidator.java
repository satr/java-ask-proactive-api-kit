package com.github.satr.aws.regions;
// Copyright © 2019, github.com/satr, MIT License

import com.amazonaws.regions.Regions;

import java.util.Arrays;

public final class RegionsValidator {
    public static boolean valid(String region) {
        return Arrays.stream(Regions.values()).anyMatch(r -> r.getName().equals(region));
    }

    public static String getValidated(String region) throws InvalidRegionNameException {
        if(!valid(region))
            throw new InvalidRegionNameException(region);
        return region;
    }
}
