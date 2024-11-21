package com.moa.moabackend.store.domain.repository;

import com.moa.moabackend.member.domain.Member;
import com.moa.moabackend.store.domain.StorePunding;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StorePundingRepository extends JpaRepository<StorePunding, Long> {

    List<StorePunding> findByMember(Member member);
    
}
