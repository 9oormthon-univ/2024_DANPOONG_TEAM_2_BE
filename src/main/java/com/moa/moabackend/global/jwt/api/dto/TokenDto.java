package com.moa.moabackend.global.jwt.api.dto;

import lombok.Builder;

@Builder
public record TokenDto(
        String accessToken
) {
}
