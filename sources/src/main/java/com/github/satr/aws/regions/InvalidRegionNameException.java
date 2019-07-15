package com.github.satr.aws.regions;
// Copyright © 2019, github.com/satr, MIT License

public class InvalidRegionNameException extends Throwable {
    public InvalidRegionNameException(String region) {
        super(String.format("Invalid region name: %s", region));
    }
}
