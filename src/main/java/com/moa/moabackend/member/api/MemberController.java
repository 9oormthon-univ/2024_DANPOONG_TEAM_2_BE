package com.moa.moabackend.member.api;

import com.moa.moabackend.global.template.RspTemplate;
import com.moa.moabackend.member.api.dto.request.InvestmentGoalUpdateReqDto;
import com.moa.moabackend.member.api.dto.request.MemberTypeUpdateReqDto;
import com.moa.moabackend.member.api.dto.response.MemberCouponsResDto;
import com.moa.moabackend.member.api.dto.response.MemberInfoResDto;
import com.moa.moabackend.member.application.MemberService;
import com.moa.moabackend.member.domain.Member;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/members")
public class MemberController implements MemberControllerDocs {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("")
    public RspTemplate<MemberInfoResDto> getUserInfo(@AuthenticationPrincipal Member member) {
        return new RspTemplate<>(HttpStatus.OK, "회원 정보 조회", memberService.getUserInfo(member.getEmail()));
    }

    @PatchMapping("/type")
    public RspTemplate<Void> updateMemberType(@AuthenticationPrincipal Member member,
                                              @RequestBody MemberTypeUpdateReqDto memberTypeUpdateReqDto) {
        memberService.updateMemberType(member.getEmail(), memberTypeUpdateReqDto.toMemberType());
        return new RspTemplate<>(HttpStatus.OK, "회원 유형 수정");
    }

    @PatchMapping("/investment-goal")
    public RspTemplate<Void> updateInvestmentGoal(@AuthenticationPrincipal Member member,
                                                  @RequestBody InvestmentGoalUpdateReqDto investmentGoalUpdateReqDto) {
        memberService.updateInvestmentGoal(member.getEmail(), investmentGoalUpdateReqDto.investmentGoal());
        return new RspTemplate<>(HttpStatus.OK, "투자 목표 수정");
    }

    @GetMapping("/coupons")
    public RspTemplate<MemberCouponsResDto> findCoupons(@AuthenticationPrincipal Member member) {
        return new RspTemplate<>(HttpStatus.OK, "내 쿠폰 조회", memberService.findCoupons(member.getEmail()));
    }

}
