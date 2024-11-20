package com.moa.moabackend.member.application;

import com.moa.moabackend.member.domain.Member;
import com.moa.moabackend.member.domain.MemberType;
import com.moa.moabackend.member.domain.repository.MemberRepository;
import com.moa.moabackend.member.exception.MemberNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
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

}
