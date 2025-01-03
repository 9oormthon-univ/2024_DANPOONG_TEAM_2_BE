package com.moa.moabackend.store.api.dto.response;

import java.time.LocalDate;
import java.util.List;

import com.moa.moabackend.store.domain.CertifiedType;

public record StoreResDto(
        Long storeId,
        Boolean isFinished,
        String name,
        String category,
        String profileImage,
        String caption,
        Long fundingTarget,
        Long fundingCurrent,
        List<String> images,
        String content,
        String address,
        Double x,
        Double y,
        List<CertifiedType> certifiedType,
        LocalDate startAt,
        LocalDate endAt,
        Long fundedCount,
        Long likeCount) {
}
