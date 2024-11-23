package com.moa.moabackend.voucher.exception;

import com.moa.moabackend.global.error.exception.InvalidGroupException;

public class MileageAlreadyReturnException extends InvalidGroupException {
    public MileageAlreadyReturnException(String message) {
        super(message);
    }

    public MileageAlreadyReturnException() {
        this("이미 환급된 마일리지입니다.");
    }
}
