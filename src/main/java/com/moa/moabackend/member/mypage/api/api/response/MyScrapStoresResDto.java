package com.moa.moabackend.member.mypage.api.api.response;

import com.moa.moabackend.store.domain.CertifiedType;
import java.util.List;
import lombok.Builder;

@Builder
public record MyScrapStoresResDto(
        List<MyScrapStoreInfoResDto> scrapStores
) {
    public static MyScrapStoresResDto from(List<MyScrapStoreInfoResDto> scrapStores) {
        return MyScrapStoresResDto.builder()
                .scrapStores(scrapStores)
                .build();
    }

    @Builder
    public record MyScrapStoreInfoResDto(
            String storeName,
            String storeContent,
            String storeImage,
            List<CertifiedTypeResDto> certifiedTypes,
            boolean isScrap
    ) {
        public static MyScrapStoreInfoResDto from(String storeName,
                                                  String storeContent,
                                                  String storeImage,
                                                  List<CertifiedType> certifiedTypes,
                                                  boolean isScrap) {
            return MyScrapStoreInfoResDto.builder()
                    .storeName(storeName)
                    .storeContent(storeContent)
                    .storeImage(storeImage)
                    .certifiedTypes(certifiedTypes.stream()
                            .map(CertifiedTypeResDto::from)
                            .toList())
                    .isScrap(isScrap)
                    .build();
        }
    }

    @Builder
    public record CertifiedTypeResDto(
            String certifiedType
    ) {
        public static CertifiedTypeResDto from(CertifiedType certifiedType) {
            return CertifiedTypeResDto.builder()
                    .certifiedType(String.valueOf(certifiedType))
                    .build();
        }
    }

}
