package com.moa.moabackend.member.domain.repository;

import com.moa.moabackend.member.domain.Member;
import com.moa.moabackend.member.domain.Mileage;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MileageRepository extends JpaRepository<Mileage, Long> {

    List<Mileage> findByMember(Member member);

}
