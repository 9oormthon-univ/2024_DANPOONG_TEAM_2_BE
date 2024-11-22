package com.moa.moabackend.store.domain;

import com.moa.moabackend.member.exception.InvalidMemberTypeException;

public enum CertifiedType {
    CO2_FOOTPRINT("탄소발자국"),
    EMPLOY_VULNERABLE_CLASS("취약계층 고용"),
    LOCAL_PRODUCT("지역생산"),
    ORGANIC("유기농"),
    RECYCLE_ENERGY("재생애너지"),
    CULTURAL_PRESERVE("문화보존"),
    ANIMAL_FRIENDLY("동물복지");

    public String description;

    CertifiedType(String description) {
        this.description = description;
    }

    public static CertifiedType fromString(String value) {
        try {
            return CertifiedType.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidMemberTypeException();
        }
    }

}
