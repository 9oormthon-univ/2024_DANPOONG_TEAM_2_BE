package com.moa.moabackend.auth.application;

import com.moa.moabackend.auth.api.dto.response.MemberLoginResDto;
import com.moa.moabackend.global.jwt.TokenProvider;
import com.moa.moabackend.global.jwt.api.dto.TokenDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class TokenService {

    private final TokenProvider tokenProvider;

    public TokenService(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Transactional
    public TokenDto getToken(MemberLoginResDto memberLoginResDto) {
        return tokenProvider.generateToken(memberLoginResDto.findMember().getEmail());
    }

}
