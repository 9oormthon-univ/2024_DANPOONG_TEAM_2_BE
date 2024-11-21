package com.moa.moabackend.voucher.api;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;

import com.moa.moabackend.global.template.RspTemplate;
import com.moa.moabackend.member.domain.Member;
import com.moa.moabackend.voucher.api.dto.request.VoucherConsumeReqDto;
import com.moa.moabackend.voucher.api.dto.request.VoucherCreateReqDto;
import com.moa.moabackend.voucher.api.dto.response.VoucherLookupResDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Voucher", description = "상품권 관련 API")
public interface VoucherControllerDocs {

    @Operation(summary = "보유한 상품권 확인", description = "사용자가 보유한 상품권 목록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "보유한 상품권 확인"),
            @ApiResponse(responseCode = "404", description = "해당 유저 앞으로 발행된 어떠한 상점 상품권도 찾지 못했습니다."),
            @ApiResponse(responseCode = "500", description = "상품권 목록을 가져오는 중 문제가 발생했습니다.")
    })
    RspTemplate<List<VoucherLookupResDto>> getUserVoucher(@AuthenticationPrincipal Member member);

    @Operation(summary = "상점 상품권 발행", description = "마일리지를 사용하여 상품권을 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "상점 상품권 구매"),
            @ApiResponse(responseCode = "400", description = "해당 상점에 대한 마일리지가 부족합니다."),
            @ApiResponse(responseCode = "404", description = "해당 유저 또는 상점을 찾을 수 없습니다."),
            @ApiResponse(responseCode = "500", description = "상품권 발행 중 문제가 발생했습니다.")
    })
    RspTemplate<String> createVoucher(
            @AuthenticationPrincipal Member member,
            @RequestBody VoucherCreateReqDto voucherCreateReqDto);

    @Operation(summary = "상점 상품권 사용", description = "보유한 상품권을 사용합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "상점 상품권 사용"),
            @ApiResponse(responseCode = "404", description = "해당 상점 또는 상점 상품권을 찾을 수 없습니다."),
            @ApiResponse(responseCode = "500", description = "상품권 사용 처리 중 문제가 발생했습니다.")
    })
    RspTemplate<Boolean> consumeVoucher(
            @AuthenticationPrincipal Member member,
            @RequestBody VoucherConsumeReqDto voucherConsumeReqDto);
}