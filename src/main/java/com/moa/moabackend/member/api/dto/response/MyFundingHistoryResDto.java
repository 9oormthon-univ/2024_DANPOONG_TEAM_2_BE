package com.moa.moabackend.member.api.dto.response;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.Builder;

@Builder
public record MyFundingHistoryResDto(
                int totalMileageAmount,
                List<HistoryInfoResDto> historyInfoResDtos) {
        public static MyFundingHistoryResDto of(int totalMileageAmount, List<HistoryInfoResDto> historyInfoResDtos) {
                return MyFundingHistoryResDto.builder()
                                .totalMileageAmount(totalMileageAmount)
                                .historyInfoResDtos(historyInfoResDtos)
                                .build();
        }

        @Builder
        public record HistoryInfoResDto(
                        Long storeId,
                        String fundingDate,
                        String storeName,
                        int amount) {
                public static HistoryInfoResDto of(
                                Long storeId,
                                LocalDateTime fundingDate,
                                String storeName,
                                int amount) {
                        String formattedDate = fundingDate.format(DateTimeFormatter.ofPattern("MM-dd"));

                        return HistoryInfoResDto.builder()
                                        .storeId(storeId)
                                        .fundingDate(formattedDate)
                                        .storeName(storeName)
                                        .amount(amount)
                                        .build();
                }
        }
}
