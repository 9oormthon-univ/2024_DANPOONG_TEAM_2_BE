package com.moa.moabackend.member.domain;

import com.moa.moabackend.global.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Coupon extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coupon_id")
    private Long id;

    private int amount;

    private String description;

    private String expirationDate;

    @Enumerated(value = EnumType.STRING)
    private CouponStatus couponStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    private Coupon(int amount, String description, String expirationDate, Member member) {
        this.amount = amount;
        this.description = description;
        this.expirationDate = expirationDate;
        this.couponStatus = CouponStatus.UNUSED;
        this.member = member;
    }

    public void updateCouponStatus() {
        this.couponStatus = this.couponStatus == CouponStatus.USED ? CouponStatus.UNUSED : CouponStatus.USED;
    }

    public boolean isAvailable() {
        return this.couponStatus == CouponStatus.UNUSED;
    }

}
