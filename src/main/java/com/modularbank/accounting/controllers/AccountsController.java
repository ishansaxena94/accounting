package com.modularbank.accounting.controllers;

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

import com.modularbank.accounting.model.Account;
import com.modularbank.accounting.requests.CreateAccountRequest;
import com.modularbank.accounting.response.CreateAccountResponse;
import com.modularbank.accounting.services.AccountsService;
import com.modularbank.accounting.validators.AccountValidator;

@RestController
@RequestMapping("/accounts")
public class AccountsController {

	@Autowired
	AccountsService accountsService;

	@PostMapping("/create")
	public ResponseEntity createAccount(@RequestBody CreateAccountRequest request) {

		Account account;
		try {
			AccountValidator.validateAccount(request);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getLocalizedMessage());
		}

		try {
			account = accountsService.createAccount(request);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getLocalizedMessage());
		}

		CreateAccountResponse responseBody = accountsService.createResponse(account);

		return ResponseEntity.ok(responseBody);

	}

	@GetMapping("/get/{customerId}")
	public ResponseEntity getAccount(@PathVariable Long accountId) {

		if (0 == accountId || null == accountId)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("AccountId should be greater than 0");

		Account account;
		try {
			account = accountsService.getAccountById(accountId);
		} catch (AccountException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getLocalizedMessage());
		}

		CreateAccountResponse responseBody = accountsService.createResponse(account);

		return ResponseEntity.ok(responseBody);
	}

	@GetMapping("/")
	public ResponseEntity base() {
		return ResponseEntity.ok("Welcome to Modular Bank");

	}

}
