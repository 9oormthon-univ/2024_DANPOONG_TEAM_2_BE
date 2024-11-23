package com.moa.moabackend.member.mypage.api.api.response;

import lombok.Builder;

@Builder
public record MyPageInfoResDto(
        String name,
        String picture,
        String memberType,
        int certifiedTypeCount
) {
    public static MyPageInfoResDto from(String name, String picture, String memberType, int certifiedTypeCount) {
        return MyPageInfoResDto.builder()
                .name(name)
                .picture(picture)
                .memberType(memberType)
                .certifiedTypeCount(certifiedTypeCount)
                .build();
    }
}
