package com.moa.moabackend.member.mypage.application;

import com.moa.moabackend.member.domain.Member;
import com.moa.moabackend.member.domain.repository.MemberRepository;
import com.moa.moabackend.member.exception.MemberNotFoundException;
import com.moa.moabackend.member.mypage.api.api.response.MyCertifiedTypesResDto;
import com.moa.moabackend.member.mypage.api.api.response.MyCertifiedTypesResDto.MyCertifiedTypeInfoResDto;
import com.moa.moabackend.member.mypage.api.api.response.MyPageInfoResDto;
import com.moa.moabackend.member.mypage.api.api.response.MyScrapStoresResDto;
import com.moa.moabackend.member.mypage.api.api.response.MyScrapStoresResDto.MyScrapStoreInfoResDto;
import com.moa.moabackend.store.domain.CertifiedType;
import com.moa.moabackend.store.domain.StoreScrap;
import com.moa.moabackend.store.domain.repository.StoreScrapRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MyPageService {

    private final MemberRepository memberRepository;
    private final StoreScrapRepository storeScrapRepository;

    // 프로필 정보 조회
    public MyPageInfoResDto findMyProfileByEmail(String email) {
        Member member = memberRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new);

        return MyPageInfoResDto.from(member.getNickname(),
                member.getPicture(),
                String.valueOf(member.getMemberType()),
                member.getCertifiedType().size());
    }

    // 찜한 프로젝트
    public MyScrapStoresResDto findMyScrapStores(String email) {
        Member member = memberRepository.findByEmail(email).orElseThrow();
        List<StoreScrap> storeScraps = storeScrapRepository.findByMember(member);

        List<MyScrapStoreInfoResDto> myScrapStoreInfoResDtos = storeScraps.stream()
                .map(storeScrap -> {
                            boolean isScrap = storeScrap.getMember().getId().equals(member.getId());

                            return MyScrapStoreInfoResDto.from(
                                    storeScrap.getStore().getName(),
                                    storeScrap.getStore().getContent(),
                                    storeScrap.getStore().getProfileImage(),
                                    storeScrap.getStore().getCertifiedType(),
                                    isScrap);
                        }

                )
                .toList();

        return MyScrapStoresResDto.from(myScrapStoreInfoResDtos);
    }

    // 내 관심 가치 목록
    public MyCertifiedTypesResDto getMyCertifiedTypes(String email) {
        Member member = memberRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new);

        List<MyCertifiedTypeInfoResDto> myCertifiedTypeInfoResDtos = new ArrayList<>();
        for (CertifiedType value : CertifiedType.values()) {
            if (member.getCertifiedType().contains(value)) {
                myCertifiedTypeInfoResDtos.add(MyCertifiedTypeInfoResDto.from(String.valueOf(value), true));
            } else {
                myCertifiedTypeInfoResDtos.add(MyCertifiedTypeInfoResDto.from(String.valueOf(value), false));
            }
        }

        return MyCertifiedTypesResDto.from(myCertifiedTypeInfoResDtos);
    }

    // 내 관심 가치 수정
    @Transactional
    public MyCertifiedTypesResDto updateMyCertifiedTypes(String email, CertifiedType certifiedType) {
        Member member = memberRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new);

        if (member.getCertifiedType().contains(certifiedType)) {
            member.getCertifiedType().remove(certifiedType);
        } else {
            member.getCertifiedType().add(certifiedType);
        }

        return getMyCertifiedTypes(email);
    }

}
