package com.moa.moabackend.store.api.dto.request;

import com.moa.moabackend.store.domain.CertifiedType;

public record GetStoreListDto(
        CertifiedType certifiedType,
        Integer page,
        Integer size) {

}
