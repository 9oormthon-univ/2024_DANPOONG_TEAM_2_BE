package com.moa.moabackend.member.mypage.api.api.response;

import java.util.List;
import lombok.Builder;

@Builder
public record MyCertifiedTypesResDto(
        List<MyCertifiedTypeInfoResDto> certifiedTypes
) {
    public static MyCertifiedTypesResDto from(List<MyCertifiedTypeInfoResDto> certifiedTypes) {
        return MyCertifiedTypesResDto.builder()
                .certifiedTypes(certifiedTypes)
                .build();
    }

    @Builder
    public record MyCertifiedTypeInfoResDto(
            String certifiedType,
            boolean isChoice
    ) {
        public static MyCertifiedTypeInfoResDto from(String certifiedType, boolean isChoice) {
            return MyCertifiedTypeInfoResDto.builder()
                    .certifiedType(certifiedType)
                    .isChoice(isChoice)
                    .build();
        }
    }

}
