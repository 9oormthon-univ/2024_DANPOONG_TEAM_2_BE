package com.moa.moabackend.global.error.exception;

public abstract class NotFoundGroupException extends RuntimeException {
    public NotFoundGroupException(String message) {
        super(message);
    }
}
