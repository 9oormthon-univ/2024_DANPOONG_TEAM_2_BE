package com.moa.moabackend.store.domain.repository;

import com.moa.moabackend.store.domain.CertifiedType;
import com.moa.moabackend.store.domain.Store;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StoreRepository extends JpaRepository<Store, Long> {
    Optional<Store> findById(Long id);

    Optional<Store> findByName(String name);

    @Override
    <S extends Store> S save(S store);

    void deleteById(Long id);
}