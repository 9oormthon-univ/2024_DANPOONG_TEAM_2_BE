package com.moa.moabackend.store.exception;

import com.moa.moabackend.global.error.exception.NotFoundGroupException;

public class StoreFundingNotFoundException extends NotFoundGroupException {
    public StoreFundingNotFoundException(String message) {
        super(message);
    }

    public StoreFundingNotFoundException() {
        this("펀딩을 하지 않았습니다.");
    }
}
