package com.moa.moabackend.voucher.api;

import com.moa.moabackend.global.template.RspTemplate;
import com.moa.moabackend.member.domain.Member;
import com.moa.moabackend.voucher.api.request.ExchangeVoucherDirectlyReqDto;
import com.moa.moabackend.voucher.api.request.ExchangeVoucherReqDto;
import com.moa.moabackend.voucher.api.request.ReturnMileageReqDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;

public interface VoucherControllerDocs {

    @Operation(summary = "마일리지 리턴받기 API", description = "마일리지를 리턴받습니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "리턴 받기 성공")
    })
    RspTemplate<Void> returnMileage(@AuthenticationPrincipal Member member,
                                    @RequestBody ReturnMileageReqDto returnMileageReqDto);

    @Operation(summary = "마일리지로 상품권 교환 API", description = "마일리지로 상품권을 교환합니다. voucherAmount는 교환할 상품권의 금액입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "상품권 교환 성공")
    })
    RspTemplate<Void> exchangeVoucher(@AuthenticationPrincipal Member member,
                                      @RequestBody ExchangeVoucherReqDto exchangeVoucherReqDto);

    @Operation(summary = "상품권 바로 교환 API", description = "상품권을 바로 교환합니다. voucherAmount는 교환할 상품권의 금액입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "리턴 받기 성공")
    })
    RspTemplate<Void> exchangeVoucherDirectly(@AuthenticationPrincipal Member member,
                                              @RequestBody ExchangeVoucherDirectlyReqDto exchangeVoucherDirectlyReqDto);

}
