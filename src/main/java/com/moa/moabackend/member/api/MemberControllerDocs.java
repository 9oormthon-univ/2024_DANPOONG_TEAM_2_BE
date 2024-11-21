package com.moa.moabackend.member.api;

import com.moa.moabackend.global.template.RspTemplate;
import com.moa.moabackend.member.api.dto.request.InvestmentGoalUpdateReqDto;
import com.moa.moabackend.member.api.dto.request.MemberTypeUpdateReqDto;
import com.moa.moabackend.member.api.dto.response.MemberCouponsResDto;
import com.moa.moabackend.member.api.dto.response.MemberInfoResDto;
import com.moa.moabackend.member.domain.Member;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;

public interface MemberControllerDocs {

    @Operation(summary = "내 정보 조회 API", description = "내 정보를 조회하는 API 입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "내 정보 조회 성공")
    })
    RspTemplate<MemberInfoResDto> getUserInfo(@AuthenticationPrincipal Member member);

    @Operation(summary = "회원 유형 변경 API", description = "회원 가입 후 회원 유형을 선택하는 API 입니다. 회원 유형은 INVESTOR 또는 BUSINESS 중 하나여야 합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원 유형 변경 성공")
    })
    RspTemplate<Void> updateMemberType(@AuthenticationPrincipal Member member,
                                       @RequestBody MemberTypeUpdateReqDto memberTypeUpdateReqDto);

    @Operation(summary = "투자 목표 변경 API", description = "투자 목표를 변경하는 API 입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "투자 목표 변경 성공")
    })
    RspTemplate<Void> updateInvestmentGoal(@AuthenticationPrincipal Member member,
                                           @RequestBody InvestmentGoalUpdateReqDto investmentGoalUpdateReqDto);

    @Operation(summary = "내 쿠폰 조회 API", description = "내 쿠폰을 조회하는 API 입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "쿠폰 조회 성공")
    })
    RspTemplate<MemberCouponsResDto> findCoupons(@AuthenticationPrincipal Member member);

}
