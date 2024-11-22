package com.moa.moabackend.funding.application;

import com.moa.moabackend.funding.api.request.FundWithAmountReqDto;
import com.moa.moabackend.funding.api.request.FundWithCouponReqDto;
import com.moa.moabackend.funding.api.response.MyFundingsResDto;
import com.moa.moabackend.funding.api.response.MyFundingsResDto.MyFundingInfoResDto;
import com.moa.moabackend.member.domain.Coupon;
import com.moa.moabackend.member.domain.Member;
import com.moa.moabackend.member.domain.repository.CouponRepository;
import com.moa.moabackend.member.domain.repository.MemberRepository;
import com.moa.moabackend.member.exception.CouponNotFoundException;
import com.moa.moabackend.member.exception.MemberNotFoundException;
import com.moa.moabackend.store.domain.Store;
import com.moa.moabackend.store.domain.StoreFunding;
import com.moa.moabackend.store.domain.repository.StoreFundingRepository;
import com.moa.moabackend.store.domain.repository.StoreRepository;
import com.moa.moabackend.store.exception.StoreNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FundingService {

    private final MemberRepository memberRepository;
    private final StoreRepository storeRepository;
    private final CouponRepository couponRepository;
    private final StoreFundingRepository storeFundingRepository;

    // 일반 금액으로 펀딩하기
    @Transactional
    public void fundWithAmount(String email, FundWithAmountReqDto fundWithAmountReqDto) {
        Member member = memberRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new);
        Store store = storeRepository.findById(fundWithAmountReqDto.storeId()).orElseThrow(StoreNotFoundException::new);

        StoreFunding storeFunding = storeFundingRepository.findByMemberAndStore(member, store)
                .orElse(null);

        if (storeFunding != null) {
            storeFunding.addAmount(fundWithAmountReqDto.amount());
        } else {
            storeFunding = StoreFunding.builder()
                    .amount(fundWithAmountReqDto.amount())
                    .member(member)
                    .store(store)
                    .build();
        }

        store.addFundingCurrent(fundWithAmountReqDto.amount());
        storeFundingRepository.save(storeFunding);
    }

    // 쿠폰으로 펀딩하기
    @Transactional
    public void fundWithCoupon(String email, FundWithCouponReqDto fundWithCouponReqDto) {
        Member member = memberRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new);
        Store store = storeRepository.findById(fundWithCouponReqDto.storeId()).orElseThrow(StoreNotFoundException::new);
        Coupon coupon = couponRepository.findById(fundWithCouponReqDto.couponId())
                .orElseThrow(CouponNotFoundException::new);

        if (!coupon.isAvailable()) {
            throw new CouponNotFoundException("이미 사용한 쿠폰입니다.");
        }

        StoreFunding storeFunding = storeFundingRepository.findByMemberAndStore(member, store)
                .orElse(null);

        if (storeFunding != null) {
            storeFunding.addAmount(coupon.getAmount());
        } else {
            storeFunding = StoreFunding.builder()
                    .amount(coupon.getAmount())
                    .member(member)
                    .store(store)
                    .build();
        }

        coupon.updateCouponStatus();
        store.addFundingCurrent(coupon.getAmount());
        storeFundingRepository.save(storeFunding);
    }

    // 내 펀딩 목록 조회
    public MyFundingsResDto getMyFundings(String email) {
        Member member = memberRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new);
        List<StoreFunding> storeFundings = storeFundingRepository.findByMember(member);

        List<MyFundingInfoResDto> myFundingInfoResDtos = storeFundings.stream()
                .map(storeFunding -> {
                            long fundingTarget = storeFunding.getStore().getFundingTarget();
                            long fundingCurrent = storeFunding.getStore().getFundingCurrent();

                            return MyFundingInfoResDto.of(
                                    storeFunding.getStore().getId(),
                                    storeFunding.getStore().getName(),
                                    storeFunding.getStore().getProfileImage(),
                                    fundingTarget > fundingCurrent,
                                    storeFunding.getAmount());
                        }

                )
                .toList();

        return MyFundingsResDto.from(myFundingInfoResDtos);
    }

}
