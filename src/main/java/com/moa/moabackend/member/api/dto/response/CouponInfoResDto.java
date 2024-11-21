package com.moa.moabackend.member.api.dto.response;

import lombok.Builder;

@Builder
public record CouponInfoResDto(
        int amount,
        String description,
        String expirationDate,
        String couponStatus
) {
    public static CouponInfoResDto from(int amount, String description, String expirationDate, String couponStatus) {
        return CouponInfoResDto.builder()
                .amount(amount)
                .description(description)
                .expirationDate(expirationDate)
                .couponStatus(couponStatus)
                .build();
    }
}
