package com.modularbank.accounting.controllers;

import java.util.List;

import javax.security.auth.login.AccountException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.modularbank.accounting.exceptions.InvalidAmountException;
import com.modularbank.accounting.exceptions.TransactionException;
import com.modularbank.accounting.model.Transaction;
import com.modularbank.accounting.requests.CreateTransactionRequest;
import com.modularbank.accounting.response.GetTransactionResponse;
import com.modularbank.accounting.services.TransactionService;
import com.modularbank.accounting.validators.TransactionValidator;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

	@Autowired
	TransactionService transactionService;

	@PostMapping("/create")
	public ResponseEntity create(@RequestBody CreateTransactionRequest request) {

		try {
			TransactionValidator.validate(request);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getLocalizedMessage());
		}

		Transaction txn;
		try {
			txn = transactionService.doTransaction(request);
		} catch (InvalidAmountException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getLocalizedMessage());
		} catch (AccountException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getLocalizedMessage());
		} catch (TransactionException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getLocalizedMessage());
		}

		return ResponseEntity.ok(txn);

	}

	@GetMapping("/get/{accountId}")
	public ResponseEntity getAllTransactions(@PathVariable Long accountId) {

		if (0 >= accountId || null == accountId)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Account Id should be greater than 0");

		List<Transaction> txns;
		try {
			txns = transactionService.fetchAllTransactionsByAccountId(accountId);
		} catch (AccountException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getLocalizedMessage());
		} catch (TransactionException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getLocalizedMessage());
		}

		List<GetTransactionResponse> response = transactionService.buildResponse(txns);

		return ResponseEntity.ok(response);

	}
}
