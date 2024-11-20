package com.moa.moabackend.member.api;

import com.moa.moabackend.global.template.RspTemplate;
import com.moa.moabackend.member.api.dto.request.MemberTypeUpdateReqDto;
import com.moa.moabackend.member.application.MemberService;
import com.moa.moabackend.member.domain.Member;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    @PatchMapping("/type")
    public RspTemplate<Void> updateMemberType(@AuthenticationPrincipal Member member,
                                              @RequestBody MemberTypeUpdateReqDto memberTypeUpdateReqDto) {
        memberService.updateMemberType(member.getEmail(), memberTypeUpdateReqDto.toMemberType());
        return new RspTemplate<>(HttpStatus.OK, "회원 유형 수정 완료");
    }

}
