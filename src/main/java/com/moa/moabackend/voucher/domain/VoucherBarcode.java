package com.moa.moabackend.voucher.domain;

import com.moa.moabackend.member.domain.Member;
import com.moa.moabackend.store.domain.Store;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
public class VoucherBarcode {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "voucher_barcode_id")
    private String id;

    private int amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @Builder
    private VoucherBarcode(int amount, Member member, Store store) {
        this.amount = amount;
        this.member = member;
        this.store = store;
    }

}
