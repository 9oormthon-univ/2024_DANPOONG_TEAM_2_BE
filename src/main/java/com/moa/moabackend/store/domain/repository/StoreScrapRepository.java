package com.moa.moabackend.store.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.moa.moabackend.store.domain.StoreScrap;

public interface StoreScrapRepository extends JpaRepository<StoreScrap, Long> {
    Long countByStore_id(Long store_id);

    Optional<StoreScrap> findByMember_idAndStore_id(Long member_id, Long store_id);

    List<StoreScrap> findByMember_id(Long member_id);

    Boolean existsByMember_idAndStore_id(Long member_id, Long store_id);
}