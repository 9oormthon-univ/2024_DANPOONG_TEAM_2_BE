package com.moa.moabackend.funding.api.request;

public record FundWithAmountReqDto(
        Long storeId,
        int amount
) {
}
