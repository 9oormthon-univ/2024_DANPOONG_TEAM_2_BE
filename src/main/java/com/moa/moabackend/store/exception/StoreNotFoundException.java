package com.moa.moabackend.store.exception;

import com.moa.moabackend.global.error.exception.NotFoundGroupException;

public class StoreNotFoundException extends NotFoundGroupException {
    public StoreNotFoundException(String message) {
        super(message);
    }

    public StoreNotFoundException() {
        this("가게를 찾을 수 없습니다.");
    }
}
