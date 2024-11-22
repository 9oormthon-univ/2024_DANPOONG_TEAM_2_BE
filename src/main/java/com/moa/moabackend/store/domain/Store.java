package com.moa.moabackend.store.domain;

import com.moa.moabackend.global.entity.BaseEntity;
import com.moa.moabackend.member.domain.Mileage;
import com.moa.moabackend.member.domain.VoucherBarcode;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Store extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id")
    private Long id;

    private String name;

    private String category;

    @Enumerated(value = EnumType.STRING)
    private List<CertifiedType> certifiedType;

    private String profileImage;

    private String caption;

    private String content;

    private long fundingCurrent;

    private long fundingTarget;

    @Column(name = "start_at")
    private LocalDate startAt;

    @Column(name = "end_at")
    private LocalDate endAt;

    @OneToOne(mappedBy = "store", orphanRemoval = true, cascade = CascadeType.ALL)
    private StoreLocation storeLocation;

    @OneToMany(mappedBy = "store", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<StoreImage> storeImages = new ArrayList<>();

    @OneToMany(mappedBy = "store", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<StoreFunding> storeFundings = new ArrayList<>();

    @OneToMany(mappedBy = "store", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<StoreScrap> storeScraps = new ArrayList<>();

    @OneToMany(mappedBy = "store", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Mileage> mileages = new ArrayList<>();

    @OneToMany(mappedBy = "store", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<VoucherBarcode> voucherBarcodes = new ArrayList<>();

    @Builder
    private Store(Long id, String name, String category, String profileImage, List<CertifiedType> certifiedType,
            String caption, String content,
            long fundingCurrent,
            long fundingTarget, StoreLocation storeLocation, LocalDate startAt, LocalDate endAt) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.profileImage = profileImage;
        this.certifiedType = certifiedType;
        this.caption = caption;
        this.content = content;
        this.fundingCurrent = fundingCurrent;
        this.fundingTarget = fundingTarget;
        this.storeLocation = storeLocation;
        this.startAt = startAt;
        this.endAt = endAt;
    }

    @Transactional
    public void updateTo(Store store) {
        this.name = store.name != null ? store.name : this.name;
        this.category = store.category != null ? store.category : this.category;
        this.profileImage = store.profileImage != null ? store.profileImage : this.profileImage;
        this.certifiedType = store.certifiedType != null ? store.certifiedType : this.certifiedType;
        this.caption = store.caption != null ? store.caption : this.caption;
        this.content = store.content != null ? store.content : this.content;
        this.fundingCurrent = store.fundingCurrent != 0 ? store.fundingCurrent : this.fundingCurrent;
        this.fundingTarget = store.fundingTarget != 0 ? store.fundingTarget : this.fundingTarget;
        this.storeLocation = store.storeLocation != null ? store.storeLocation : this.storeLocation;
        this.startAt = store.startAt != null ? store.startAt : this.startAt;
        this.endAt = store.endAt != null ? store.endAt : this.endAt;
    }

}
