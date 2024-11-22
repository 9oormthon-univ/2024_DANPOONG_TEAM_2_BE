package com.moa.moabackend.voucher.application;

import com.moa.moabackend.global.entity.Status;
import com.moa.moabackend.member.domain.Member;
import com.moa.moabackend.member.domain.repository.MemberRepository;
import com.moa.moabackend.member.exception.MemberNotFoundException;
import com.moa.moabackend.store.domain.Store;
import com.moa.moabackend.store.domain.StoreFunding;
import com.moa.moabackend.store.domain.repository.StoreFundingRepository;
import com.moa.moabackend.store.domain.repository.StoreRepository;
import com.moa.moabackend.store.exception.StoreFundingNotFoundException;
import com.moa.moabackend.store.exception.StoreNotFoundException;
import com.moa.moabackend.voucher.api.request.ExchangeVoucherDirectlyReqDto;
import com.moa.moabackend.voucher.api.request.ExchangeVoucherReqDto;
import com.moa.moabackend.voucher.api.request.ReturnMileageReqDto;
import com.moa.moabackend.voucher.domain.Voucher;
import com.moa.moabackend.voucher.domain.repository.VoucherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VoucherService {

    private final MemberRepository memberRepository;
    private final StoreRepository storeRepository;
    private final StoreFundingRepository storeFundingRepository;
    private final VoucherRepository voucherRepository;

    // 마일리지 리턴받기
    @Transactional
    public void returnMileage(String email, ReturnMileageReqDto returnMileageReqDto) {
        Member member = memberRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new);
        Store store = storeRepository.findById(returnMileageReqDto.storeId()).orElseThrow(StoreNotFoundException::new);

        StoreFunding storeFunding = storeFundingRepository.findByMemberAndStore(member, store)
                .orElseThrow(StoreFundingNotFoundException::new);

        if (storeFunding.getStatus() == Status.UN_ACTIVE) {
            throw new IllegalStateException("이미 마일리지 반환받았습니다.");
        }

        storeFunding.updateStatus();
        member.addMileage(storeFunding.getAmount());
    }

    // 마일리지로 상품권 교환하기
    @Transactional
    public void exchangeVoucher(String email, ExchangeVoucherReqDto exchangeVoucherReqDto) {
        Member member = memberRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new);

        if (member.getMileage() < exchangeVoucherReqDto.voucherAmount()) {
            throw new IllegalStateException("마일리지가 부족합니다.");
        }

        member.minusMileage(exchangeVoucherReqDto.voucherAmount());

        voucherRepository.save(Voucher.builder()
                .amount(exchangeVoucherReqDto.voucherAmount())
                .member(member)
                .build());
    }

    // 바로 상품권 교환하기
    @Transactional
    public void exchangeVoucherDirectly(String email, ExchangeVoucherDirectlyReqDto exchangeVoucherDirectlyReqDto) {
        Member member = memberRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new);
        Store store = storeRepository.findById(exchangeVoucherDirectlyReqDto.storeId())
                .orElseThrow(StoreNotFoundException::new);

        StoreFunding storeFunding = storeFundingRepository.findByMemberAndStore(member, store)
                .orElseThrow(StoreFundingNotFoundException::new);

        if (storeFunding.getStatus() == Status.UN_ACTIVE) {
            throw new IllegalStateException("이미 마일리지 반환받았습니다.");
        }

        storeFunding.updateStatus();
        voucherRepository.save(Voucher.builder()
                .amount(exchangeVoucherDirectlyReqDto.voucherAmount())
                .member(member)
                .build());
    }

}
