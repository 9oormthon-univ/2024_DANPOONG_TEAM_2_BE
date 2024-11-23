package com.moa.moabackend.member.api;

import com.moa.moabackend.global.template.RspTemplate;
import com.moa.moabackend.member.api.dto.request.FavoriteCertificatedType;
import com.moa.moabackend.member.api.dto.request.InvestmentGoalUpdateReqDto;
import com.moa.moabackend.member.api.dto.request.MemberTypeUpdateReqDto;
import com.moa.moabackend.member.api.dto.response.MemberCouponsResDto;
import com.moa.moabackend.member.api.dto.response.MemberInfoResDto;
import com.moa.moabackend.member.api.dto.response.MyFundingHistoryResDto;
import com.moa.moabackend.member.domain.Member;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;

public interface MemberControllerDocs {

        @Operation(summary = "내 정보 조회 API, 포트폴리오 뷰에서 사용", description = "내 정보를 조회하는 API 입니다. 포트폴리오 뷰에서 사용하시면 됩니다. // 반환: 사용자 정보 + 마일리지")
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

        @Operation(summary = "내 펀딩 내역 조회 API", description = "내 펀딩 내역을 조회하는 API 입니다.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "펀딩 내역 조회 성공")
        })
        RspTemplate<MyFundingHistoryResDto> findMyPundingHistory(@AuthenticationPrincipal Member member);

        @Operation(summary = "관심분야 기반 상점 추천", description = "사전에 지정된 CertifiedType에 따라 각각 가장 펀딩 수가 많은 상점 하나씩을 추천합니다.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "조건에 일치하는 상점 리스트를 정상적으로 조회했습니다."),
                        @ApiResponse(responseCode = "404", description = "조건에 일치하는 상점이 없습니다."),
                        @ApiResponse(responseCode = "500", description = "상점 리스트를 불러오는 중 문제가 발생했습니다.")
        })
        RspTemplate<Boolean> setFavoriteCertifiedTypes(@AuthenticationPrincipal Member member,
                        @RequestBody FavoriteCertificatedType payload);
}
