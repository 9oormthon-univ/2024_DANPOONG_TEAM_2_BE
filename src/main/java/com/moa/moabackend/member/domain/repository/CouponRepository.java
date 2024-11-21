package com.moa.moabackend.member.domain.repository;

import com.moa.moabackend.member.domain.Coupon;
import com.moa.moabackend.member.domain.Member;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CouponRepository extends JpaRepository<Coupon, Long> {

    @Query("""
                select c
                from Coupon c
                where c.member = :member and c.couponStatus = 'UNUSED'
            """)
    List<Coupon> findByMember(@Param("member") Member member);

}
