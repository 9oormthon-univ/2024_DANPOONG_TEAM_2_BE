package com.moa.moabackend.store.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.moa.moabackend.store.domain.StoreScrap;

public interface StoreScrapRepository extends JpaRepository<StoreScrap, Long> {
    Long countByStore_id(Long store_id);
}