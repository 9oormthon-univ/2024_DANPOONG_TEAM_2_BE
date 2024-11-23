package com.moa.moabackend.funding.api;

import com.moa.moabackend.funding.api.request.FundWithAmountReqDto;
import com.moa.moabackend.funding.api.request.FundWithCouponReqDto;
import com.moa.moabackend.funding.api.response.MyFundingsResDto;
import com.moa.moabackend.funding.api.response.MyFundingsReturnResDto;
import com.moa.moabackend.global.template.RspTemplate;
import com.moa.moabackend.member.domain.Member;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;

public interface FundingControllerDocs {

    @Operation(summary = "일반 금액 펀딩 API", description = "일반 돈으로 펀딩하는 API 입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "펀딩 성공")
    })
    RspTemplate<Void> fundWithAmount(@AuthenticationPrincipal Member member,
                                     @RequestBody FundWithAmountReqDto fundWithAmountReqDto);

    @Operation(summary = "쿠폰 펀딩 API", description = "쿠폰으로 펀딩하는 API 입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "펀딩 성공")
    })
    RspTemplate<Void> fundWithCoupon(@AuthenticationPrincipal Member member,
                                     @RequestBody FundWithCouponReqDto fundWithCouponReqDto);

    @Operation(summary = "내 펀딩 정보 조회 API - 교환하기 뷰에서 사용", description = "내가 펀딩한 정보를 조회하는 API 입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "내 펀딩 조회 성공")
    })
    RspTemplate<MyFundingsResDto> getMyFundings(@AuthenticationPrincipal Member member);

    @Operation(summary = "내 펀딩 리턴 확인 조회 API - 리턴 확인하기 뷰에서 사용", description = "내 펀딩 리턴 확인을 조회하는 API 입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "내 펀딩 리턴 확인 성공")
    })
    RspTemplate<MyFundingsReturnResDto> getMyFundingsReturn(@AuthenticationPrincipal Member member);

}
