package com.moa.moabackend.store.domain.repository;

import com.moa.moabackend.member.domain.Member;
import com.moa.moabackend.store.domain.StoreScrap;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreScrapRepository extends JpaRepository<StoreScrap, Long> {
    Long countByStore_id(Long store_id);

    Optional<StoreScrap> findByMember_idAndStore_id(Long member_id, Long store_id);

    List<StoreScrap> findByMember_id(Long member_id);

    List<StoreScrap> findByMember(Member member);

    Boolean existsByMember_idAndStore_id(Long member_id, Long store_id);
}