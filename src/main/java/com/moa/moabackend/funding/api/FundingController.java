package com.moa.moabackend.funding.api;

import com.moa.moabackend.funding.api.request.FundWithAmountReqDto;
import com.moa.moabackend.funding.api.request.FundWithCouponReqDto;
import com.moa.moabackend.funding.api.response.MyFundingsResDto;
import com.moa.moabackend.funding.api.response.MyFundingsReturnResDto;
import com.moa.moabackend.funding.application.FundingService;
import com.moa.moabackend.global.template.RspTemplate;
import com.moa.moabackend.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/fundings")
public class FundingController implements FundingControllerDocs {

    private final FundingService fundingService;

    @PostMapping("/amount")
    public RspTemplate<Void> fundWithAmount(@AuthenticationPrincipal Member member,
                                            @RequestBody FundWithAmountReqDto fundWithAmountReqDto) {
        fundingService.fundWithAmount(member.getEmail(), fundWithAmountReqDto);
        return new RspTemplate<>(HttpStatus.OK, "금액으로 펀딩");
    }

    @PostMapping("/coupon")
    public RspTemplate<Void> fundWithCoupon(@AuthenticationPrincipal Member member,
                                            @RequestBody FundWithCouponReqDto fundWithCouponReqDto) {
        fundingService.fundWithCoupon(member.getEmail(), fundWithCouponReqDto);
        return new RspTemplate<>(HttpStatus.OK, "쿠폰으로 펀딩");
    }

    @GetMapping("/my")
    public RspTemplate<MyFundingsResDto> getMyFundings(@AuthenticationPrincipal Member member) {
        return new RspTemplate<>(HttpStatus.OK, "내 펀딩 정보 조회", fundingService.getMyFundings(member.getEmail()));
    }

    @GetMapping("/my/return")
    public RspTemplate<MyFundingsReturnResDto> getMyFundingsReturn(@AuthenticationPrincipal Member member) {
        return new RspTemplate<>(HttpStatus.OK,
                "내 펀딩 리턴 확인 정보 조회",
                fundingService.getMyFundingsReturn(member.getEmail()));
    }
}
