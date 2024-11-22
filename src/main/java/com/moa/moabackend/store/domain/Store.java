package com.moa.moabackend.store.domain;

import com.moa.moabackend.global.entity.BaseEntity;
import com.moa.moabackend.member.domain.Mileage;
import com.moa.moabackend.voucher.domain.Voucher;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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

    private String profileImage;

    private String caption;

    private String content;

    private long fundingCurrent;

    private long fundingTarget;

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
    private List<Voucher> vouchers = new ArrayList<>();

    @Builder
    private Store(Long id, String name, String category, String profileImage, String caption, String content,
                  long fundingCurrent,
                  long fundingTarget, StoreLocation storeLocation) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.profileImage = profileImage;
        this.caption = caption;
        this.content = content;
        this.fundingCurrent = fundingCurrent;
        this.fundingTarget = fundingTarget;
        this.storeLocation = storeLocation;
    }

}
