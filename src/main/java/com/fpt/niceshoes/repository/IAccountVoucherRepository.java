package com.fpt.niceshoes.repository;

import com.fpt.niceshoes.entity.AccountVoucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IAccountVoucherRepository extends JpaRepository<AccountVoucher, Long> {
    AccountVoucher findByAccountIdAndVoucherId(Long idAccount, Long idVoucher);
    List<AccountVoucher> findByVoucherId(Long id);
}
