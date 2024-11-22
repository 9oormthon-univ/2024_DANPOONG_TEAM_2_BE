package com.moa.moabackend.store.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.moa.moabackend.store.domain.StoreLocation;

public interface StoreLocationRepository extends JpaRepository<StoreLocation, Long> {
        @Query(value = "SELECT * FROM store_location " +
                        "WHERE (6371 * acos(cos(radians(:latitude)) * cos(radians(latitude)) * " +
                        "cos(radians(longitude) - radians(:longitude)) + sin(radians(:latitude)) * " +
                        "sin(radians(latitude)))) <= :distance", nativeQuery = true)
        Optional<List<StoreLocation>> findStoresWithinDistance(
                        @Param("longitude") double x,
                        @Param("latitude") double y,
                        @Param("distance") long radius);

        @Override
        <S extends StoreLocation> S save(S storeLocation);

        @Modifying
        @Query("DELETE FROM StoreLocation sl WHERE sl.store.id = :storeId")
        void deleteByStoreId(@Param("storeId") Long storeId);

        @Query("SELECT sl FROM StoreLocation sl WHERE sl.address LIKE %:address%")
        Optional<List<StoreLocation>> findByAddressContaining(@Param("address") String address);
}
