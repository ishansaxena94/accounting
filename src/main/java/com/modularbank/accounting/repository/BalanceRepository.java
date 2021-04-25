package com.modularbank.accounting.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.modularbank.accounting.model.Balance;

public interface BalanceRepository extends JpaRepository<Balance, Long> {

	// List<Balance> findByAccountId(Long accountId);

	@Override
	List<Balance> findAll();
}
