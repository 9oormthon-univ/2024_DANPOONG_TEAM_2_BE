package com.moa.moabackend.auth.api;

import com.moa.moabackend.auth.api.dto.request.TokenReqDto;
import com.moa.moabackend.global.jwt.api.dto.TokenDto;
import com.moa.moabackend.global.template.RspTemplate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface AuthControllerDocs {

    @Operation(summary = "자체 AccessToken 발급", description = "code를 이용하여 액세스 토큰을 발급합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "토큰 발급 성공")
    })
    RspTemplate<TokenDto> generateAccessToken(
            @Parameter(name = "provider", description = "소셜 타입(kakao)", in = ParameterIn.PATH)
            @PathVariable(name = "provider") String provider,
            @RequestBody TokenReqDto tokenReqDto);

}
