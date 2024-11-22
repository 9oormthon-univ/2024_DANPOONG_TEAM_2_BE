package com.moa.moabackend.voucher.domain.repository;

import com.moa.moabackend.voucher.domain.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoucherRepository extends JpaRepository<Voucher, Long> {
}
