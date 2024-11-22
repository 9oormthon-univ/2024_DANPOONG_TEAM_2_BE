package com.moa.moabackend.store.domain.repository;

import com.moa.moabackend.member.domain.Member;
import com.moa.moabackend.store.domain.Store;
import com.moa.moabackend.store.domain.StoreFunding;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreFundingRepository extends JpaRepository<StoreFunding, Long> {

    List<StoreFunding> findByMember(Member member);

    Optional<StoreFunding> findByMemberAndStore(Member member, Store store);

}
