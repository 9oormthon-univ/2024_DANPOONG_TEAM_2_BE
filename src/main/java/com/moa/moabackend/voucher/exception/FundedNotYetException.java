package com.moa.moabackend.voucher.exception;

import com.moa.moabackend.global.error.exception.InvalidGroupException;

public class FundedNotYetException extends InvalidGroupException {
    public FundedNotYetException(String message) {
        super(message);
    }

    public FundedNotYetException() {
        this("유저가 아직까지 펀딩하지 않은 상점입니다.");
    }
}