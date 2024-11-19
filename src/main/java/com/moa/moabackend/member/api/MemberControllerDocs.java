package com.moa.moabackend.member.api;

import com.moa.moabackend.global.template.RspTemplate;
import com.moa.moabackend.member.api.dto.request.MemberTypeUpdateReqDto;
import com.moa.moabackend.member.domain.Member;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;

public interface MemberControllerDocs {

    @Operation(summary = "회원 유형 변경 API", description = "회원 가입 후 회원 유형을 선택하는 API 입니다. 회원 유형은 INVESTOR 또는 BUSINESS 중 하나여야 합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원 유형 변경 성공")
    })
    RspTemplate<Void> updateMemberType(@AuthenticationPrincipal Member member,
                                       @RequestBody MemberTypeUpdateReqDto memberTypeUpdateReqDto);

}
