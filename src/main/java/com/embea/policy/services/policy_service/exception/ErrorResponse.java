package com.embea.policy.services.policy_service.exception;

import lombok.Data;

import java.util.List;

@Data
public class ErrorResponse extends Exception {
    private final String message;
    private final List<String> details;
    private boolean suppressStackTrace = false;

    public ErrorResponse(String message, List<String> details) {
        super(message, null, true, false);
        this.message = message;
        this.details = details;
        this.suppressStackTrace = true;
    }

    public ErrorResponse(String message, List<String> details, boolean suppressStackTrace) {
        super(message, null, suppressStackTrace, !suppressStackTrace);
        this.message = message;
        this.details = details;
        this.suppressStackTrace = suppressStackTrace;
    }

    @Override
    public String toString() {
        return this.suppressStackTrace ? getLocalizedMessage() : super.toString();
    }
}
