package com.moa.moabackend.voucher.application;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.moa.moabackend.member.domain.Member;
import com.moa.moabackend.member.domain.Mileage;
import com.moa.moabackend.member.domain.repository.MemberRepository;
import com.moa.moabackend.member.domain.repository.MileageRepository;
import com.moa.moabackend.store.domain.Store;
import com.moa.moabackend.store.domain.repository.StoreRepository;
import com.moa.moabackend.voucher.api.dto.request.VoucherConsumeReqDto;
import com.moa.moabackend.voucher.api.dto.request.VoucherCreateReqDto;
import com.moa.moabackend.voucher.domain.VoucherBarcode;
import com.moa.moabackend.voucher.domain.repository.VoucherRepository;
import com.moa.moabackend.voucher.exception.FundedNotYetException;
import com.moa.moabackend.voucher.exception.NotEnoughMileageException;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VoucherService {

    private final MemberRepository memberRepository;
    private final MileageRepository mileageRepository;
    private final StoreRepository storeRepository;
    private final VoucherRepository voucherRepository;

    @Transactional(readOnly = true)
    public List<VoucherBarcode> getVoucherFromUser(Long userId) {
        List<VoucherBarcode> voucherBarcode = voucherRepository.findAllByMember_id(userId);
        if (voucherBarcode.isEmpty()) {
            throw new EntityNotFoundException("해당 유저로 발행된 상점 상품권이 없습니다.");
        }

        return voucherBarcode;
    }

    @Transactional
    public void createVoucher(Long userId, VoucherCreateReqDto payload) {
        // 1. 유저 검색
        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("해당 유저를 찾을 수 없습니다."));

        System.out.println("member -> OK");
        System.out.println("payload = " + payload);
        System.out.println("storeId = " + payload.storeId());
        System.out.println("amount = " + payload.amount());

        // 2. 상점 검색
        Store store = storeRepository.findById(payload.storeId())
                .orElseThrow(() -> new EntityNotFoundException("해당 상점을 찾을 수 없습니다."));

        // 3. 마일리지 차감
        Mileage mileage = mileageRepository.findByMember_idAndStore_id(userId, payload.storeId())
                .orElseThrow(() -> new FundedNotYetException());
        ;
        if (mileage.getAmount() - payload.amount() < 0) {
            throw new NotEnoughMileageException();
        }

        mileage.updateAmount(mileage.getAmount() - payload.amount());

        // 3. 해당 유저 앞으로 발행될 상점 상품권 객체 생성
        VoucherBarcode voucherBarcode = VoucherBarcode.builder().amount(payload.amount()).store(store)
                .member(member)
                .build();

        // 4. 상품권 발행
        voucherRepository.save(voucherBarcode);
    }

    @Transactional
    public void consumeVoucher(Long userId, VoucherConsumeReqDto payload) {
        // 1. 상점 상품권 객체 찾기
        VoucherBarcode voucherBarcode = voucherRepository
                .findByMember_idAndStore_idAndId(userId, payload.storeId(), payload.voucherBarcode())
                .orElseThrow(() -> new EntityNotFoundException("해당 유저가 가지고 있는 상점 상품권을 찾을 수 없습니다."));

        // 2. 상점 상품권 사용
        voucherRepository.delete(voucherBarcode);
    }
}
