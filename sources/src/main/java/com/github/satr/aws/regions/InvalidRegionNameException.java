package com.github.satr.aws.regions;

public class InvalidRegionNameException extends Throwable {
    public InvalidRegionNameException(String region) {
        super(String.format("Invalid region name: %s", region));
    }
}
