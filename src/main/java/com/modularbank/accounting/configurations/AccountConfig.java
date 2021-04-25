package com.modularbank.accounting.configurations;

import java.util.HashSet;
import java.util.Set;

import org.springframework.context.annotation.Configuration;

@Configuration
public class AccountConfig {

	public static Set<String> supportedCurrencies = new HashSet<>();

	static {
		supportedCurrencies.add("EUR");
		supportedCurrencies.add("SEK");
		supportedCurrencies.add("GBP");
		supportedCurrencies.add("USD");
	}
}
