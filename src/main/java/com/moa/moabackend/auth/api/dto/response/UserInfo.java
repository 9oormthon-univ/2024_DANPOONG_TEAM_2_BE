package com.moa.moabackend.auth.api.dto.response;

public record UserInfo(
        String email,
        String name,
        String nickname,
        String picture
) {
}
