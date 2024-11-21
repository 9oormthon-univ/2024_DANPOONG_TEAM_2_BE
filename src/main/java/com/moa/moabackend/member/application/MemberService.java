package com.moa.moabackend.member.application;

import com.moa.moabackend.member.api.dto.response.CouponInfoResDto;
import com.moa.moabackend.member.api.dto.response.MemberCouponsResDto;
import com.moa.moabackend.member.api.dto.response.MemberInfoResDto;
import com.moa.moabackend.member.domain.Coupon;
import com.moa.moabackend.member.domain.Member;
import com.moa.moabackend.member.domain.MemberType;
import com.moa.moabackend.member.domain.Mileage;
import com.moa.moabackend.member.domain.repository.CouponRepository;
import com.moa.moabackend.member.domain.repository.MemberRepository;
import com.moa.moabackend.member.domain.repository.MileageRepository;
import com.moa.moabackend.member.exception.MemberNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final CouponRepository couponRepository;
    private final MileageRepository mileageRepository;

    public MemberInfoResDto getUserInfo(String email) {
        Member member = memberRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new);
        List<Mileage> mileages = mileageRepository.findByMember(member);

        int totalMileageAmount = mileages.stream()
                .mapToInt(Mileage::getAmount)
                .sum();

        return MemberInfoResDto.of(member.getEmail(),
                member.getPicture(),
                member.getNickname(),
                member.getInvestmentGoal(),
                String.valueOf(member.getMemberType()),
                totalMileageAmount);
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
                .map(coupon ->
                        CouponInfoResDto.of(
                                coupon.getAmount(),
                                coupon.getDescription(),
                                coupon.getExpirationDate(),
                                String.valueOf(coupon.getCouponStatus())))
                .toList();

        return MemberCouponsResDto.from(couponInfoResDtos);
    }

}
