package com.moa.moabackend.funding.api.response;

import java.util.List;
import lombok.Builder;

@Builder
public record MyFundingsReturnResDto(
        List<MyFundingReturnInfoResDto> myFundingInfoResDtos
) {
    public static MyFundingsReturnResDto from(List<MyFundingReturnInfoResDto> myFundingInfoResDtos) {
        return MyFundingsReturnResDto.builder()
                .myFundingInfoResDtos(myFundingInfoResDtos)
                .build();
    }

    @Builder
    public record MyFundingReturnInfoResDto(
            String storeName,
            boolean isReturnCompleted,
            int myFundingAmount
    ) {
        public static MyFundingReturnInfoResDto of(String storeName,
                                                   boolean isReturnCompleted,
                                                   int myFundingAmount) {
            return MyFundingReturnInfoResDto.builder()
                    .storeName(storeName)
                    .isReturnCompleted(isReturnCompleted)
                    .myFundingAmount(myFundingAmount)
                    .build();

        }
    }

}
