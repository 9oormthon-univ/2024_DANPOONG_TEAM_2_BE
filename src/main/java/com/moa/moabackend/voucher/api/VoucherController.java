package com.moa.moabackend.voucher.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.moa.moabackend.global.template.RspTemplate;
import com.moa.moabackend.member.domain.Member;
import com.moa.moabackend.voucher.api.dto.request.VoucherConsumeReqDto;
import com.moa.moabackend.voucher.api.dto.request.VoucherCreateReqDto;
import com.moa.moabackend.voucher.api.dto.response.VoucherLookupResDto;
import com.moa.moabackend.voucher.application.VoucherService;
import com.moa.moabackend.voucher.domain.VoucherBarcode;
import com.moa.moabackend.voucher.exception.FundedNotYetException;
import com.moa.moabackend.voucher.exception.NotEnoughMileageException;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/voucher")
@RequiredArgsConstructor
public class VoucherController {

    private final VoucherService voucherService;

    @GetMapping("")
    public RspTemplate<List<VoucherLookupResDto>> getUserVoucher(@AuthenticationPrincipal Member member) {
        List<VoucherLookupResDto> result = new ArrayList<VoucherLookupResDto>();

        try {
            // 1. 유저의 상품권 목록 가져오기
            List<VoucherBarcode> voucherBarcode = voucherService.getVoucherFromUser(member.getId());

            // 2. 받아온 상품권 정보들을 상품권 DTO로 매핑
            for (VoucherBarcode voucher : voucherBarcode) {
                result.add(new VoucherLookupResDto(
                        voucher.getId(),
                        voucher.getStore().getId(),
                        voucher.getAmount()));
            }
        } catch (EntityNotFoundException e) {
            return new RspTemplate<>(HttpStatus.NOT_FOUND, "해당 유저 앞으로 발행된 어떠한 상점 상품권도 찾지 못했습니다.", null);
        } catch (Exception e) {
            e.printStackTrace();
            return new RspTemplate<>(HttpStatus.INTERNAL_SERVER_ERROR, "상품권 목록을 가져오는 중 문제가 발생했습니다.", null);
        }

        return new RspTemplate<>(HttpStatus.OK, "보유한 상품권 확인", result);
    }

    @PostMapping("")
    public RspTemplate<String> createVoucher(@AuthenticationPrincipal Member member,
            @RequestBody VoucherCreateReqDto voucherCreateReqDto) {
        try {
            voucherService.createVoucher(member.getId(), voucherCreateReqDto);
        } catch (EntityNotFoundException e) {
            return new RspTemplate<>(HttpStatus.NOT_FOUND, "해당 유저 또는 상점을 찾을 수 없습니다.", null);
        } catch (NotEnoughMileageException e) {
            return new RspTemplate<>(HttpStatus.BAD_REQUEST, "해당 상점에 대한 마일리지가 부족합니다.", null);
        } catch (FundedNotYetException e) {
            return new RspTemplate<>(HttpStatus.BAD_REQUEST, "유저가 아직까지 펀딩하지 않은 상점입니다.", null);
        } catch (Exception e) {
            e.printStackTrace();
            return new RspTemplate<>(HttpStatus.INTERNAL_SERVER_ERROR, "상품권 발행 중 문제가 발생했습니다.", null);
        }
        return new RspTemplate<>(HttpStatus.OK, "상점 상품권 구매", null);
    }

    @DeleteMapping("")
    public RspTemplate<Boolean> consumeVoucher(@AuthenticationPrincipal Member member,
            @RequestBody VoucherConsumeReqDto voucherConsumeReqDto) {
        try {
            voucherService.consumeVoucher(member.getId(), voucherConsumeReqDto);
        } catch (EntityNotFoundException e) {
            return new RspTemplate<>(HttpStatus.NOT_FOUND, "해당 상점 또는 상점 상품권을 찾을 수 없습니다.", null);
        } catch (Exception e) {
            e.printStackTrace();
            return new RspTemplate<>(HttpStatus.INTERNAL_SERVER_ERROR, "상품권 사용 처리 중 문제가 발생했습니다.", null);
        }
        return new RspTemplate<>(HttpStatus.OK, "상점 상품권 사용", true);
    }

}