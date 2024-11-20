package com.moa.moabackend.auth.application;

import com.moa.moabackend.auth.api.dto.response.MemberLoginResDto;
import com.moa.moabackend.auth.api.dto.response.UserInfo;
import com.moa.moabackend.member.domain.Member;
import com.moa.moabackend.member.domain.Role;
import com.moa.moabackend.member.domain.SocialType;
import com.moa.moabackend.member.domain.repository.MemberRepository;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class AuthMemberService {
    private final MemberRepository memberRepository;

    public AuthMemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
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

        return memberRepository.save(
                Member.builder()
                        .email(userInfo.email())
                        .nickname(userInfo.nickname())
                        .picture(userPicture)
                        .socialType(provider)
                        .investmentGoal("투자 그래프 끌어~올려~!")
                        .role(Role.ROLE_USER)
                        .build()
        );
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
