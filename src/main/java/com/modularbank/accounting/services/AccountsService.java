package com.modularbank.accounting.services;

import java.time.Duration;
import java.time.Instant;
import java.util.LinkedList;
import java.util.List;

import javax.security.auth.login.AccountException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.modularbank.accounting.mapper.AccountMapper;
import com.modularbank.accounting.model.Account;
import com.modularbank.accounting.model.Balance;
import com.modularbank.accounting.pubsub.AccountPublisher;
import com.modularbank.accounting.repository.AccountsRepository;
import com.modularbank.accounting.requests.CreateAccountRequest;
import com.modularbank.accounting.response.BalanceResponse;
import com.modularbank.accounting.response.CreateAccountResponse;

@Service
public class AccountsService {

	@Autowired
	AccountsRepository accountsRepository;

	@Autowired
	private AccountMapper accountMapper;

	@Autowired
	AccountPublisher publisher;

	public Account getAccountById(Long accountId) throws AccountException {
		Instant start = Instant.now();

		Account account = accountsRepository.findById(accountId)
				.orElseThrow(() -> new AccountException("Account with accountId" + accountId + "does not exist"));

		Instant finish = Instant.now();
		System.out.println("DB Operation getAccountById time: " + Duration.between(start, finish).toMillis());

		// accountMapper.getAccountByCustomerId(customerId);

		return account;

	}

	public Account createAccount(CreateAccountRequest request) throws Exception {

		Account createdAccount = mapToAccountDTO(request);

		try {
			createdAccount = accountsRepository.save(createdAccount);
		} catch (Exception e) {
			throw new AccountException(e.getLocalizedMessage());
		}

		if (null == createdAccount) {
			System.out.println("Account is null");
			throw new AccountException("Unable to create account");
		}

		publisher.publish(createdAccount, "CREATE");

		return createdAccount;
	}

	public CreateAccountResponse createResponse(Account account) {

		List<BalanceResponse> balances = new LinkedList<>();
		BalanceResponse balanceResponse;

		for (Balance balance : account.getBalances()) {
			balanceResponse = new BalanceResponse();
			balanceResponse.setCurrency(balance.getCurrency());
			balanceResponse.setAmount(balance.getAmount());
			balances.add(balanceResponse);
		}

		CreateAccountResponse response = CreateAccountResponse.builder().accountId(account.getAccountId())
				.customerId(account.getCustomerId()).balances(balances).build();

		return response;
	}

	private Account mapToAccountDTO(CreateAccountRequest request) {

		Account account = Account.builder().customerId(request.getCustomerId()).country(request.getCountry()).build();

		List<Balance> balances = new LinkedList<>();
		Balance balance;

		for (String currency : request.getCurrencies()) {
			balance = new Balance();
			balance.setCurrency(currency);
			balance.setAmount(0.0);
			balances.add(balance);
		}

		account.setBalances(balances);

		return account;
	}

}
