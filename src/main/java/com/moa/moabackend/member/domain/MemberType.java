package com.moa.moabackend.member.domain;

import com.moa.moabackend.member.exception.InvalidMemberTypeException;

public enum MemberType {
    INVESTOR("투자자"),
    BUSINESS("업체");

    private String description;

    MemberType(String description) {
        this.description = description;
    }

    public static MemberType fromString(String value) {
        try {
            return MemberType.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidMemberTypeException();
        }
    }

}
