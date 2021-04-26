package com.modularbank.accounting.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SimpleController {

	@GetMapping("/")
	public ResponseEntity homePage() {
		return ResponseEntity.ok("Welcome to Modular Bank");
	}
}
