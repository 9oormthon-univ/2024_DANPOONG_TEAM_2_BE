package com.moa.moabackend.voucher.exception;

import com.moa.moabackend.global.error.exception.InvalidGroupException;

public class InsufficientMileageException extends InvalidGroupException {
    public InsufficientMileageException(String message) {
        super(message);
    }

    public InsufficientMileageException() {
        this("마일리지가 부족합니다.");
    }
}
