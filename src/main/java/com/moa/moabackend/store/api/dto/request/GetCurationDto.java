package com.moa.moabackend.store.api.dto.request;

import com.moa.moabackend.store.domain.CertifiedType;

public record GetCurationDto(
        Integer type,
        CertifiedType certifiedType) {
}
