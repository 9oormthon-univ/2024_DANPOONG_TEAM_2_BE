package com.moa.moabackend.store.exception;

import com.moa.moabackend.global.error.exception.NotFoundGroupException;

public class RevGeocodeNotFoundException extends NotFoundGroupException {
    public RevGeocodeNotFoundException(String message) {
        super(message);
    }

    public RevGeocodeNotFoundException() {
        this("해당하는 좌표로 가게를 찾지 못했습니다.");
    }
}
