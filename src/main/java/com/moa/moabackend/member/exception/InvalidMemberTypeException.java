package com.moa.moabackend.member.exception;

import com.moa.moabackend.global.error.exception.InvalidGroupException;

public class InvalidMemberTypeException extends InvalidGroupException {
    public InvalidMemberTypeException(String message) {
        super(message);
    }

    public InvalidMemberTypeException() {
        this("유효하지 않은 회원 유형입니다. INVESTOR 또는 BUSINESS 중 하나여야 합니다.");
    }
}
