package com.moa.moabackend.store.exception;

import com.moa.moabackend.global.error.exception.InvalidGroupException;

public class AlreadyScrapedException extends InvalidGroupException {
    AlreadyScrapedException(String message) {
        super(message);
    }

    public AlreadyScrapedException() {
        this("이미 찜한 상점입니다.");
    }
}
