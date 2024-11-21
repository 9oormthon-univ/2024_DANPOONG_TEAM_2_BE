package com.moa.moabackend.auth.application;

import com.moa.moabackend.auth.api.dto.response.MemberLoginResDto;
import com.moa.moabackend.auth.api.dto.response.UserInfo;
import com.moa.moabackend.member.domain.Coupon;
import com.moa.moabackend.member.domain.Member;
import com.moa.moabackend.member.domain.Role;
import com.moa.moabackend.member.domain.SocialType;
import com.moa.moabackend.member.domain.repository.CouponRepository;
import com.moa.moabackend.member.domain.repository.MemberRepository;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class AuthMemberService {

    private final MemberRepository memberRepository;
    private final CouponRepository couponRepository;

    public AuthMemberService(MemberRepository memberRepository, CouponRepository couponRepository) {
        this.memberRepository = memberRepository;
        this.couponRepository = couponRepository;
    }

    @Transactional
    public MemberLoginResDto saveUserInfo(UserInfo userInfo, SocialType provider) {
        Member member = getExistingMemberOrCreateNew(userInfo, provider);

        return MemberLoginResDto.from(member);
    }

    private Member getExistingMemberOrCreateNew(UserInfo userInfo, SocialType provider) {
        return memberRepository.findByEmail(userInfo.email()).orElseGet(() -> createMember(userInfo, provider));
    }

    private Member createMember(UserInfo userInfo, SocialType provider) {
        String userPicture = getUserPicture(userInfo.picture());

        Member member = Member.builder()
                .email(userInfo.email())
                .nickname(userInfo.nickname())
                .picture(userPicture)
                .socialType(provider)
                .investmentGoal("투자 그래프 끌어~올려~!")
                .role(Role.ROLE_USER)
                .build();

        couponRepository.save(Coupon.builder()
                .amount(10000)
                .description("신규 회원 무료 펀딩 쿠폰")
                .expirationDate("2024.11.23~2024.11.24")
                .member(member)
                .build());

        return memberRepository.save(member);
    }

    private String getUserPicture(String picture) {
        return Optional.ofNullable(picture)
                .map(this::convertToHighRes)
                .orElseThrow(null);
    }

    private String convertToHighRes(String url) {
        return url.replace("s96-c", "s2048-c");
    }

}
