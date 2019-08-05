package com.github.satr.common;
// Copyright © 2019, github.com/satr, MIT License

public interface OperationValueResult<T> extends OperationResult {
    void setValue(T value);
    T getValue();
    OperationValueResult<T> withValue(T value);
}

