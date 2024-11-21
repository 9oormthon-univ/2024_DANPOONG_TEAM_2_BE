package com.moa.moabackend.voucher.api.dto.request;

public record VoucherCreateReqDto(
        Long storeId,
        Integer amount) {
}
