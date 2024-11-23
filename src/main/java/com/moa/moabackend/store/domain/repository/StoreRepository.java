package com.moa.moabackend.store.domain.repository;

import com.moa.moabackend.store.domain.Store;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Long> {
    Optional<Store> findById(Long id);

    Optional<Store> findByName(String name);

    @Override
    <S extends Store> S save(S store);

    void deleteById(Long id);
}