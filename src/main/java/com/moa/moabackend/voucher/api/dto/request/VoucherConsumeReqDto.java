package com.moa.moabackend.voucher.api.dto.request;

public record VoucherConsumeReqDto(
        Long storeId,
        String voucherBarcode) {
}
