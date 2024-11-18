package com.moa.moabackend.auth.api;

import com.moa.moabackend.auth.api.dto.request.TokenReqDto;
import com.moa.moabackend.auth.api.dto.response.MemberLoginResDto;
import com.moa.moabackend.auth.api.dto.response.UserInfo;
import com.moa.moabackend.auth.application.AuthMemberService;
import com.moa.moabackend.auth.application.AuthService;
import com.moa.moabackend.auth.application.AuthServiceFactory;
import com.moa.moabackend.auth.application.TokenService;
import com.moa.moabackend.global.jwt.api.dto.TokenDto;
import com.moa.moabackend.global.template.RspTemplate;
import com.moa.moabackend.member.domain.SocialType;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController implements AuthControllerDocs {

    private final AuthServiceFactory authServiceFactory;
    private final AuthMemberService memberService;
    private final TokenService tokenService;

    @PostMapping("/{provider}/token")
    public RspTemplate<TokenDto> generateAccessToken(
            @Parameter(name = "provider", description = "소셜 타입(kakao)", in = ParameterIn.PATH)
            @PathVariable(name = "provider") String provider,
            @RequestBody TokenReqDto tokenReqDto) {
        AuthService authService = authServiceFactory.getAuthService(provider);
        UserInfo userInfo = authService.getUserInfo(tokenReqDto.code());

        MemberLoginResDto getMemberDto = memberService.saveUserInfo(userInfo,
                SocialType.valueOf(provider.toUpperCase()));
        TokenDto getToken = tokenService.getToken(getMemberDto);

        return new RspTemplate<>(HttpStatus.OK, "토큰 발급", getToken);
    }

}
