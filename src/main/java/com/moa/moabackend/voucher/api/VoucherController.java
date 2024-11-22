package com.moa.moabackend.voucher.api;

import com.moa.moabackend.global.template.RspTemplate;
import com.moa.moabackend.member.domain.Member;
import com.moa.moabackend.voucher.api.request.ExchangeVoucherDirectlyReqDto;
import com.moa.moabackend.voucher.api.request.ExchangeVoucherReqDto;
import com.moa.moabackend.voucher.api.request.ReturnMileageReqDto;
import com.moa.moabackend.voucher.application.VoucherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/vouchers")
public class VoucherController implements VoucherControllerDocs {

    private final VoucherService voucherService;

    @PostMapping("/return-mileage")
    public RspTemplate<Void> returnMileage(@AuthenticationPrincipal Member member,
                                           @RequestBody ReturnMileageReqDto returnMileageReqDto) {
        voucherService.returnMileage(member.getEmail(), returnMileageReqDto);
        return new RspTemplate<>(HttpStatus.OK, "마일리지 리턴");
    }

    @PostMapping("/exchange-voucher")
    public RspTemplate<Void> exchangeVoucher(@AuthenticationPrincipal Member member,
                                             @RequestBody ExchangeVoucherReqDto exchangeVoucherReqDto) {
        voucherService.exchangeVoucher(member.getEmail(), exchangeVoucherReqDto);
        return new RspTemplate<>(HttpStatus.OK, "마일리지로 상품권 교환");
    }

    @PostMapping("/exchange-voucher-directly")
    public RspTemplate<Void> exchangeVoucherDirectly(@AuthenticationPrincipal Member member,
                                                     @RequestBody ExchangeVoucherDirectlyReqDto exchangeVoucherDirectlyReqDto) {
        voucherService.exchangeVoucherDirectly(member.getEmail(), exchangeVoucherDirectlyReqDto);
        return new RspTemplate<>(HttpStatus.OK, "상품권 바로 교환");
    }

}
