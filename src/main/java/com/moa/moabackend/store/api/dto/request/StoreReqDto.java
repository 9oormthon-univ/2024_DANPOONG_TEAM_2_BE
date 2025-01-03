package com.moa.moabackend.store.api.dto.request;

import java.time.LocalDate;
import java.util.List;

import com.moa.moabackend.store.domain.CertifiedType;

public record StoreReqDto(
        String name,
        String category,
        String profileImage,
        String caption,
        Long fundingTarget,
        List<String> images,
        String content,
        Double x,
        Double y,
        List<CertifiedType> certifiedType,
        LocalDate startAt,
        LocalDate endAt) {

}
