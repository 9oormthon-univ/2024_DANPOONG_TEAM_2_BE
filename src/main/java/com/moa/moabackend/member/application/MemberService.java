package com.moa.moabackend.member.application;

import com.moa.moabackend.member.api.dto.response.CouponInfoResDto;
import com.moa.moabackend.member.api.dto.response.MemberCouponsResDto;
import com.moa.moabackend.member.api.dto.response.MemberInfoResDto;
import com.moa.moabackend.member.api.dto.response.MyFundingHistoryResDto;
import com.moa.moabackend.member.api.dto.response.MyFundingHistoryResDto.HistoryInfoResDto;
import com.moa.moabackend.member.domain.Coupon;
import com.moa.moabackend.member.domain.Member;
import com.moa.moabackend.member.domain.MemberType;
import com.moa.moabackend.member.domain.repository.CouponRepository;
import com.moa.moabackend.member.domain.repository.MemberRepository;
import com.moa.moabackend.member.exception.MemberNotFoundException;
import com.moa.moabackend.store.domain.CertifiedType;
import com.moa.moabackend.store.domain.StoreFunding;
import com.moa.moabackend.store.domain.repository.StoreFundingRepository;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final CouponRepository couponRepository;
    private final StoreFundingRepository storeFundingRepository;

    public MemberInfoResDto getUserInfo(String email) {
        Member member = memberRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new);

        List<StoreFunding> storeFundings = storeFundingRepository.findByMember(member);

        Map<CertifiedType, Long> certifiedTypeCount = storeFundings.stream()
                .flatMap(storeFunding -> storeFunding.getStore().getCertifiedType().stream())
                .collect(Collectors.groupingBy(certifiedType -> certifiedType, Collectors.counting()));

        CertifiedType mostCertifiedType = certifiedTypeCount.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);

        String favoriteCertifiedType = mostCertifiedType != null ? mostCertifiedType.toString() : "";

        return MemberInfoResDto.of(member.getEmail(),
                member.getPicture(),
                member.getNickname(),
                member.getInvestmentGoal(),
                String.valueOf(member.getMemberType()),
                member.getMileage(),
                favoriteCertifiedType);
    }

    @Transactional
    public void updateMemberType(String email, MemberType memberType) {
        Member member = memberRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new);
        member.updateMemberType(memberType);
    }

    @Transactional
    public void updateInvestmentGoal(String email, String investmentGoal) {
        Member member = memberRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new);
        member.updateInvestmentGoal(investmentGoal);
    }

    public MemberCouponsResDto findCoupons(String email) {
        Member member = memberRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new);
        List<Coupon> coupons = couponRepository.findByMember(member);

        List<CouponInfoResDto> couponInfoResDtos = coupons.stream()
                .map(coupon -> CouponInfoResDto.of(
                        coupon.getId(),
                        coupon.getAmount(),
                        coupon.getDescription(),
                        coupon.getExpirationDate(),
                        String.valueOf(coupon.getCouponStatus())))
                .toList();

        return MemberCouponsResDto.from(couponInfoResDtos);
    }

    public MyFundingHistoryResDto getMyFundingHistory(String email) {
        Member member = memberRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new);
        List<StoreFunding> storeFundings = storeFundingRepository.findByMember(member);

        List<HistoryInfoResDto> historyInfoResDtos = storeFundings.stream()
                .map(storeFunding -> HistoryInfoResDto.of(
                        storeFunding.getStore().getId(),
                        storeFunding.getCreatedAt(),
                        storeFunding.getStore().getName(),
                        storeFunding.getAmount()))
                .toList();

        return MyFundingHistoryResDto.of(member.getMileage(), historyInfoResDtos);
    }

    @Transactional
    public void setFavoriteCertifiedTypes(String email, List<CertifiedType> certifiedTypes) {
        Member member = memberRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new);
        member.updateCertifiedTypes(certifiedTypes);
    }

}
