package com.moa.moabackend.voucher.exception;

import com.moa.moabackend.global.error.exception.InvalidGroupException;

public class NotEnoughMileageException extends InvalidGroupException {
    public NotEnoughMileageException(String message) {
        super(message);
    }

    public NotEnoughMileageException() {
        this("해당 상점에 대한 마일리지가 부족합니다.");
    }
}