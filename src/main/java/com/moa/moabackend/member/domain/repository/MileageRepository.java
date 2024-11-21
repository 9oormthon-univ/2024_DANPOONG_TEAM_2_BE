package com.moa.moabackend.member.domain.repository;

import com.moa.moabackend.member.domain.Member;
import com.moa.moabackend.member.domain.Mileage;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MileageRepository extends JpaRepository<Mileage, Long> {

    List<Mileage> findByMember(Member member);

    Optional<Mileage> findByMember_idAndStore_id(Long member_id, Long store_id);

}
