package com.moa.moabackend.voucher.api.dto.response;

public record VoucherLookupResDto(
        String id,
        Long storeId,
        int amount) {
}
