package com.moa.moabackend.global.error.exception;

public abstract class AccessDeniedGroupException extends RuntimeException {
    public AccessDeniedGroupException(String message) {
        super(message);
    }
}
