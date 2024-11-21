package com.moa.moabackend.member.domain;

import com.moa.moabackend.global.entity.BaseEntity;
import com.moa.moabackend.store.domain.StorePunding;
import com.moa.moabackend.store.domain.StoreScrap;
import com.moa.moabackend.voucher.domain.VoucherBarcode;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String email;

    private String picture;

    private String nickname;

    private String investmentGoal;

    @Enumerated(value = EnumType.STRING)
    private SocialType socialType;

    @Enumerated(value = EnumType.STRING)
    private MemberType memberType;

    @OneToMany(mappedBy = "member", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<StorePunding> storePundings = new ArrayList<>();

    @OneToMany(mappedBy = "member", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<StoreScrap> storeScraps = new ArrayList<>();

    @OneToMany(mappedBy = "member", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Mileage> mileages = new ArrayList<>();

    @OneToMany(mappedBy = "member", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<VoucherBarcode> voucherBarcodes = new ArrayList<>();

    @OneToMany(mappedBy = "member", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Coupon> coupons = new ArrayList<>();

    @Builder
    private Member(Role role, String email, String picture, String nickname, String investmentGoal,
            SocialType socialType) {
        this.role = role;
        this.email = email;
        this.picture = picture;
        this.nickname = nickname;
        this.investmentGoal = investmentGoal;
        this.socialType = socialType;
    }

    public void updateMemberType(MemberType memberType) {
        this.memberType = memberType;
    }

    public void updateInvestmentGoal(String investmentGoal) {
        this.investmentGoal = investmentGoal;
    }

}
