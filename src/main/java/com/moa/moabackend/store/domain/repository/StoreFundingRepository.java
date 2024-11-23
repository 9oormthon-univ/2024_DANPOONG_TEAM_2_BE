package com.moa.moabackend.store.domain.repository;

import com.moa.moabackend.member.domain.Member;
import com.moa.moabackend.store.domain.Store;
import com.moa.moabackend.store.domain.StoreFunding;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StoreFundingRepository extends JpaRepository<StoreFunding, Long> {

    List<StoreFunding> findByMember(Member member);

    Optional<StoreFunding> findByMemberAndStore(Member member, Store store);

    Long countByStore_id(Long store_id);

    @Query("""
                    SELECT sf
                    FROM StoreFunding sf
                    WHERE sf.member = :member AND sf.status = 'UN_ACTIVE'
            """)
    List<StoreFunding> findByMemberAndStatus(@Param("member") Member member);
}
