package com.moa.moabackend.funding.api.request;

public record FundWithCouponReqDto(
        Long storeId,
        Long couponId
) {
}
