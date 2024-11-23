package com.moa.moabackend.member.mypage.api;

import com.moa.moabackend.global.template.RspTemplate;
import com.moa.moabackend.member.domain.Member;
import com.moa.moabackend.member.mypage.api.api.request.UpdateCertifiedTypeReqDto;
import com.moa.moabackend.member.mypage.api.api.response.MyCertifiedTypesResDto;
import com.moa.moabackend.member.mypage.api.api.response.MyPageInfoResDto;
import com.moa.moabackend.member.mypage.api.api.response.MyScrapStoresResDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;

public interface MyPageControllerDocs {

    @Operation(summary = "마이페이지 내 정보 조회 API", description = "마이페이지에서 내 정보를 조회하는 API 입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "내 정보 조회 성공")
    })
    RspTemplate<MyPageInfoResDto> myProfileInfo(@AuthenticationPrincipal Member member);

    @Operation(summary = "마이페이지 내 찜한 프로젝트 조회 API", description = "마이페이지에서 내 찜한 프로젝트를 조회하는 API 입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "내 찜한 프로젝트 조회 성공")
    })
    RspTemplate<MyScrapStoresResDto> getMyScrapStores(@AuthenticationPrincipal Member member);

    @Operation(summary = "마이페이지 내 관심 가치 조회 API", description = "마이페이지에서 내 관심 가치를 조회하는 API 입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "내 관심 가치 조회 성공")
    })
    RspTemplate<MyCertifiedTypesResDto> getMyCertifiedTypes(@AuthenticationPrincipal Member member);

    @Operation(summary = "마이페이지 내 관심 가치 수정 API", description = "마이페이지에서 내 관심 가치를 수정하는 API 입니다. 리스트, 배열아닙니다. 단일 값입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "내 관심 가치 수정 성공")
    })
    RspTemplate<MyCertifiedTypesResDto> updateMyCertifiedTypes(@AuthenticationPrincipal Member member,
                                                               @RequestBody UpdateCertifiedTypeReqDto updateCertifiedTypeReqDto);
}
