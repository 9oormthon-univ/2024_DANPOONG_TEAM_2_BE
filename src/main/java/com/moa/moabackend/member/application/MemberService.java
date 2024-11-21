package com.moa.moabackend.member.application;

import com.moa.moabackend.member.api.dto.response.CouponInfoResDto;
import com.moa.moabackend.member.api.dto.response.MemberCouponsResDto;
import com.moa.moabackend.member.domain.Coupon;
import com.moa.moabackend.member.domain.Member;
import com.moa.moabackend.member.domain.MemberType;
import com.moa.moabackend.member.domain.repository.CouponRepository;
import com.moa.moabackend.member.domain.repository.MemberRepository;
import com.moa.moabackend.member.exception.MemberNotFoundException;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final CouponRepository couponRepository;

    public MemberService(MemberRepository memberRepository, CouponRepository couponRepository) {
        this.memberRepository = memberRepository;
        this.couponRepository = couponRepository;
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
                        CouponInfoResDto.from(
                                coupon.getAmount(),
                                coupon.getDescription(),
                                coupon.getExpirationDate(),
                                String.valueOf(coupon.getCouponStatus())))
                .toList();

        return MemberCouponsResDto.from(couponInfoResDtos);
    }

}
