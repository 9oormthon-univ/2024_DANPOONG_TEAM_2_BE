package com.moa.moabackend.member.api.dto.response;

import lombok.Builder;

@Builder
public record CouponInfoResDto(
        Long couponId,
        int amount,
        String description,
        String expirationDate,
        String couponStatus
) {
    public static CouponInfoResDto of(Long couponId,
                                      int amount,
                                      String description,
                                      String expirationDate,
                                      String couponStatus) {
        return CouponInfoResDto.builder()
                .couponId(couponId)
                .amount(amount)
                .description(description)
                .expirationDate(expirationDate)
                .couponStatus(couponStatus)
                .build();
    }
}
