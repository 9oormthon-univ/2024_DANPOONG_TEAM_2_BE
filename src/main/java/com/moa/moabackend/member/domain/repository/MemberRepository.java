package com.moa.moabackend.member.domain.repository;

import com.moa.moabackend.member.domain.Member;
import com.moa.moabackend.store.domain.CertifiedType;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);
}
