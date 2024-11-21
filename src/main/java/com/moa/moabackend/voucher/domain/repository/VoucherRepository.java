package com.moa.moabackend.voucher.domain.repository;

import com.moa.moabackend.voucher.domain.VoucherBarcode;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoucherRepository extends JpaRepository<VoucherBarcode, String> {

    Optional<VoucherBarcode> findByMember_id(Long member_id);

    Optional<VoucherBarcode> findById(String id);

    Optional<VoucherBarcode> findByMember_idAndStore_id(Long member_id, Long store_id);

    Optional<VoucherBarcode> findByMember_idAndStore_idAndId(Long member_id, Long store_id, String id);

    List<VoucherBarcode> findAllByMember_id(Long member_id);

    void deleteById(String id);

    <V extends VoucherBarcode> V save(V voucherBarcode);
}
