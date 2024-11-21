package com.moa.moabackend.member.api.dto.response;

import java.util.List;
import lombok.Builder;

@Builder
public record MemberCouponsResDto(
        List<CouponInfoResDto> couponInfoResDtos
) {
    public static MemberCouponsResDto from(List<CouponInfoResDto> couponInfoResDtos) {
        return MemberCouponsResDto.builder()
                .couponInfoResDtos(couponInfoResDtos)
                .build();
    }
}
