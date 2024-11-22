package com.moa.moabackend.voucher.exception;

import com.moa.moabackend.global.error.exception.NotFoundGroupException;

public class VoucherNotFoundException extends NotFoundGroupException {
    public VoucherNotFoundException(String message) {
        super(message);
    }

    public VoucherNotFoundException() {
        this("해당 상품권을 찾을 수 없습니다.");
    }

}
