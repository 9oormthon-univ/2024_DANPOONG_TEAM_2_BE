package com.moa.moabackend.store.domain.repository;

import com.moa.moabackend.member.domain.Member;
import com.moa.moabackend.store.domain.StoreFunding;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreFundingRepository extends JpaRepository<StoreFunding, Long> {

    List<StoreFunding> findByMember(Member member);

    Long countByStore_id(Long store_id);
}
