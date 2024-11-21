package com.moa.moabackend.store.exception;

import com.moa.moabackend.global.error.exception.NotFoundGroupException;

public class StoreNotFoundException extends NotFoundGroupException {
    public StoreNotFoundException(String message) {
        super(message);
    }

    public StoreNotFoundException() {
        this("해당 상점이 존재하지 않습니다.");
    }
}
