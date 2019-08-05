package com.github.satr.common;
// Copyright © 2019, github.com/satr, MIT License

import java.util.LinkedList;
import java.util.List;

public class OperationResultImpl implements OperationResult {
    private final List<String> history = new LinkedList<>();
    private final List<String> errors = new LinkedList<>();
    private final List<String> infos = new LinkedList<>();
    private final List<String> warning = new LinkedList<>();
    private final List<String> verbose = new LinkedList<>();
    private int successErrorsSize;

    @Override
    public void addError(String format, Object... args) {
        addMessage("Error", errors, format, args);
    }

    @Override
    public void addError(Exception ex) {
        addMessage("Error", errors, ex.getMessage());
    }

    @Override
    public OperationResult withError(String format, Object... args) {
        addError(format, args);
        return this;
    }

    @Override
    public OperationResult withError(Exception ex) {
        addError(ex);
        return this;
    }

    @Override
    public void addInfo(String format, Object... args) {
        addMessage("Info", infos, format, args);
    }

    @Override
    public OperationResult withInfo(String format, Object... args) {
        addInfo(format, args);
        return this;
    }

    @Override
    public void addWarning(String format, Object... args) {
        addMessage("Warning", warning, format, args);
    }

    @Override
    public void addVerbose(String format, Object... args) {
        addMessage("Verbose", verbose, format, args);
    }

    private void addMessage(String title, List<String> messages, String format, Object... args) {
        String message = String.format(format, args);
        messages.add(message);
        history.add(String.format("%s: %s", title, message));
    }

    @Override
    public List<String> getHistory() {
        return history;
    }

    @Override
    public List<String> getErrors() {
        return errors;
    }

    @Override
    public List<String> getInfos() {
        return infos;
    }

    @Override
    public List<String> getWarning() {
        return warning;
    }

    @Override
    public List<String> getVerbose() {
        return verbose;
    }

    @Override
    public String getErrorsAsString() {
        return String.join("\n", errors);
    }

    @Override
    public String getInfosAsString() {
        return String.join("\n", infos);
    }

    @Override
    public String getWarningAsString() {
        return String.join("\n", warning);
    }

    @Override
    public String getVerboseAsString() {
        return String.join("\n", verbose);
    }

    @Override
    public String getHistoryAsString() {
        return String.join("\n", history);
    }

    @Override
    public boolean isSuccess() {
        return errors.size() <= successErrorsSize;
    }

    @Override
    public boolean isFailed() {
        return !isSuccess();
    }

    @Override
    public void resetSuccessState() {
        successErrorsSize = errors.size();
    }
}
