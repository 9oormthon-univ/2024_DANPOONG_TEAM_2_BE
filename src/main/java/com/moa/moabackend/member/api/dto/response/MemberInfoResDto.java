package com.moa.moabackend.member.api.dto.response;

import lombok.Builder;

@Builder
public record MemberInfoResDto(
        String email,
        String picture,
        String nickname,
        String investmentGoal,
        String memberType,
        int totalMileageAmount,
        String favoriteCertifiedType
) {
    public static MemberInfoResDto of(String email, String picture, String nickname, String investmentGoal,
                                      String memberType, int totalMileageAmount, String favoriteCertifiedType) {
        return MemberInfoResDto.builder()
                .email(email)
                .picture(picture)
                .nickname(nickname)
                .investmentGoal(investmentGoal)
                .memberType(memberType)
                .totalMileageAmount(totalMileageAmount)
                .favoriteCertifiedType(favoriteCertifiedType)
                .build();
    }
}
