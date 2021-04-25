package com.modularbank.accounting.controllers;

import org.springframework.web.bind.annotation.GetMapping;

public class HealthController {

	@GetMapping("/health")
	public String get() {

		return "Alive";
	}
}
