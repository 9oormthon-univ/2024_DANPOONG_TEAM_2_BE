package com.moa.moabackend.funding.api.response;

import java.util.List;
import lombok.Builder;

@Builder
public record MyFundingsResDto(
        List<MyFundingInfoResDto> myFundingInfoResDtos
) {
    public static MyFundingsResDto from(List<MyFundingInfoResDto> myFundingInfoResDtos) {
        return MyFundingsResDto.builder()
                .myFundingInfoResDtos(myFundingInfoResDtos)
                .build();
    }

    @Builder
    public record MyFundingInfoResDto(
            Long storeId,
            String storeName,
            String storePicture,
            boolean isProjectCompleted,
            int myFundingAmount
    ) {
        public static MyFundingInfoResDto of(Long storeId,
                                             String storeName,
                                             String storePicture,
                                             boolean isProjectCompleted,
                                             int myFundingAmount) {
            return MyFundingInfoResDto.builder()
                    .storeId(storeId)
                    .storeName(storeName)
                    .storePicture(storePicture)
                    .isProjectCompleted(isProjectCompleted)
                    .myFundingAmount(myFundingAmount)
                    .build();
        }
    }

}
