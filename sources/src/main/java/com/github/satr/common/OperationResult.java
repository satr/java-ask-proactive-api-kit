package com.github.satr.common;
// Copyright © 2019, github.com/satr, MIT License

import java.util.List;

public interface OperationResult {
    void addError(String format, Object... args);
    void addError(Exception ex);
    OperationResult withError(String format, Object... args);
    OperationResult withError(Exception ex);
    void addInfo(String format, Object... args);
    OperationResult withInfo(String format, Object... args);
    void addWarning(String format, Object... args);
    void addVerbose(String format, Object... args);
    List<String> getHistory();
    List<String> getErrors();
    List<String> getInfos();
    List<String> getWarning();
    List<String> getVerbose();
    String getErrorsAsString();
    String getInfosAsString();
    String getWarningAsString();
    String getVerboseAsString();
    String getHistoryAsString();
    boolean isSuccess();
    boolean isFailed();
    void resetSuccessState();
}
