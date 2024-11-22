package com.moa.moabackend.store.domain;

import com.moa.moabackend.global.entity.BaseEntity;
import com.moa.moabackend.global.entity.Status;
import com.moa.moabackend.member.domain.Member;
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
public class StoreFunding extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_punding_id")
    private Long id;

    private int amount;

    @Enumerated(EnumType.STRING)
    private Status status;
  
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @Builder
    private StoreFunding(int amount, Member member, Store store) {
        this.amount = amount;
        this.status = Status.ACTIVE;
        this.member = member;
        this.store = store;
    }

    public void updateStatus() {
        this.status = this.status == Status.ACTIVE ? Status.UN_ACTIVE : Status.ACTIVE;
    }

}
