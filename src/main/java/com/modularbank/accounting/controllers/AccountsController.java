package com.modularbank.accounting.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.modularbank.accounting.repository.AccountsRepository;

@RestController
public class AccountsController {

	@Autowired
	AccountsRepository accountsRepository;

	@GetMapping("/test")
	public String get() {

		String str = accountsRepository.findAll().stream().map(p -> p.toString()).reduce(",", String::concat);
		System.out.println(str);
		return str;
	}
}
