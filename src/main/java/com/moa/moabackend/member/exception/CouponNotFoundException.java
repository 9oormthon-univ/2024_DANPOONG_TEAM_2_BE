package com.moa.moabackend.member.exception;

import com.moa.moabackend.global.error.exception.NotFoundGroupException;

public class CouponNotFoundException extends NotFoundGroupException {
    public CouponNotFoundException(String message) {
        super(message);
    }

    public CouponNotFoundException() {
        this("쿠폰을 찾을 수 없습니다.");
    }
}
