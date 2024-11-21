package com.moa.moabackend.store.domain.repository;

import com.moa.moabackend.store.domain.StoreImage;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreImageRepository extends JpaRepository<StoreImage, Long> {
    Optional<StoreImage> findById(Long id);

    @Override
    <S extends StoreImage> S save(S storeImage);

    void deleteByStore_Id(Long storeId);

    // @Modifying
    // @Query("UPDATE StoreImage s SET s = :storeImage WHERE s.id = :id")
    // void updateStoreById(Long id, StoreImage storeImage);

    void deleteById(Long id);

}