package com.moa.moabackend.member.mypage.api.api.request;

import com.moa.moabackend.store.domain.CertifiedType;

public record UpdateCertifiedTypeReqDto(
        CertifiedType certifiedType
) {
}
