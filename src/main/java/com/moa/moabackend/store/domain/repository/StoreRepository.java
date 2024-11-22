package com.moa.moabackend.store.domain.repository;

import com.moa.moabackend.store.domain.Store;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Long> {
    Optional<Store> findById(Long id);

    Optional<Store> findByName(String name);

    @Override
    <S extends Store> S save(S store);

    // @Modifying
    // @Transactional
    // @Query("UPDATE Store s SET s.name = :name, s.category = :category,
    // s.profileImage = :profileImage, s.caption = :caption, s.content = :content,
    // s.fundingTarget = :fundingTarget, s.endAt = :endAt, s.certifiedType =
    // :certifiedType WHERE s.id = :id")
    // void updateStoreInfo(@Param("id") Long id, @Param("name") String name,
    // @Param("category") String category,
    // @Param("profileImage") String profileImage, @Param("caption") String caption,
    // @Param("content") String content, @Param("fundingTarget") Long fundingTarget,
    // @Param("end_at") LocalDate endAt, @Param("certifiedType") List<CertifiedType>
    // certifiedType);

    // @Modifying
    // @Transactional
    // @Query("UPDATE Store s SET s = :store WHERE s.id = :id")
    // void updateStoreById(@Param("id") Long id, @Param("store") Store store);

    void deleteById(Long id);

}