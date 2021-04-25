package com.modularbank.accounting.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.modularbank.accounting.model.Account;

@Repository
public interface AccountsRepository extends JpaRepository<Account, Long> {
	List<Account> findByCustomerId(Long customerId);

	@Override
	List<Account> findAll();
}
