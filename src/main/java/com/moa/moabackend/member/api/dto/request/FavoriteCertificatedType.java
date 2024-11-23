package com.moa.moabackend.member.api.dto.request;

import java.util.List;

import com.moa.moabackend.store.domain.CertifiedType;

public record FavoriteCertificatedType(
        List<CertifiedType> certifiedType) {

}
