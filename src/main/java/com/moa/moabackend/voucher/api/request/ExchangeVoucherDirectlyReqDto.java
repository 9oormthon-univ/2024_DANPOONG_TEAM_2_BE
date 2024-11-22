package com.moa.moabackend.voucher.api.request;

public record ExchangeVoucherDirectlyReqDto(
        Long storeId,
        int voucherAmount
) {
}
