package com.modularbank.accounting.services;

import java.util.LinkedList;
import java.util.List;

import javax.security.auth.login.AccountException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.modularbank.accounting.exceptions.InvalidAmountException;
import com.modularbank.accounting.exceptions.TransactionException;
import com.modularbank.accounting.model.Account;
import com.modularbank.accounting.model.Balance;
import com.modularbank.accounting.model.Transaction;
import com.modularbank.accounting.pubsub.AccountPublisher;
import com.modularbank.accounting.pubsub.TransactionPublisher;
import com.modularbank.accounting.repository.AccountsRepository;
import com.modularbank.accounting.repository.TransactionRepository;
import com.modularbank.accounting.requests.CreateTransactionRequest;
import com.modularbank.accounting.response.GetTransactionResponse;

@Service
public class TransactionService {

	@Autowired
	TransactionRepository txnRepository;

	@Autowired
	AccountsRepository accountsRepository;

	@Autowired
	TransactionPublisher txnPublisher;

	@Autowired
	AccountPublisher accPublisher;

	@Transactional
	public Transaction doTransaction(CreateTransactionRequest req)
			throws InvalidAmountException, TransactionException, AccountException {

		// [START]critical section
		Account account = accountsRepository.findById(req.getAccountId())
				.orElseThrow(() -> new AccountException("Invalid AccountId"));

		Transaction txn = mapRequestToDTO(req);
		Double newBalance = 0D;

		for (Balance bal : account.getBalances()) {
			if (bal.getCurrency().equals(req.getCurrency())) {

				if ("IN".equalsIgnoreCase(req.getDirection()))
					newBalance = bal.getAmount() + req.getAmount();
				else {
					newBalance = bal.getAmount() - req.getAmount();

					if (newBalance < 0) {
						throw new InvalidAmountException("Withdraw amount is greater than balance amount");
					}
				}

				bal.setAmount(newBalance);
				break;
			}
		}

		// DB
		accountsRepository.save(account);
		txn = txnRepository.save(txn);
		// [END] critical section

		if (null == txn) {
			throw new TransactionException("Unable to execute transaction");
		}

		txn.setBalanceAfterTransaction(newBalance);

		txnPublisher.publish(txn, "CREATE");
		accPublisher.publish(account, "UPDATE");

		return txn;
	}

	public List<Transaction> fetchAllTransactionsByAccountId(Long accountId)
			throws TransactionException, AccountException {

		Account account = accountsRepository.findById(accountId)
				.orElseThrow(() -> new AccountException("Invalid AccountId"));

		List<Transaction> transactions = txnRepository.findByAccountId(accountId);

		if (null == transactions) {
			throw new TransactionException("Unable to fetch all transaction for an accountId");
		}

		return transactions;
	}

	public List<GetTransactionResponse> buildResponse(List<Transaction> txns) {

		List<GetTransactionResponse> response = new LinkedList<>();
		GetTransactionResponse txnResponse;

		for (Transaction txn : txns) {

			txnResponse = GetTransactionResponse.builder().accountId(txn.getAccountId()).transactionId(txn.getId())
					.amount(txn.getAmount()).currency(txn.getCurrency()).direction(txn.getDirection())
					.description(txn.getDescription()).build();
			response.add(txnResponse);
		}

		return response;

	}

	private Transaction mapRequestToDTO(CreateTransactionRequest req) {
		Transaction transaction = Transaction.builder().accountId(req.getAccountId()).amount(req.getAmount())
				.currency(req.getCurrency()).direction(req.getDirection()).description(req.getDescription()).build();

		return transaction;
	}
}
