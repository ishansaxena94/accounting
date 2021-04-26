package com.modularbank.accounting.validators;

import java.util.Set;
import java.util.stream.Collectors;

import com.google.common.base.Preconditions;
import com.modularbank.accounting.configurations.AccountConfig;
import com.modularbank.accounting.requests.CreateAccountRequest;
import com.modularbank.accounting.requests.CreateTransactionRequest;

public class CurrencyValidator {

	public static void validateTransaction(CreateTransactionRequest request) {

		String msg = "Supported currencies are: " + String.join(", ", AccountConfig.supportedCurrencies);
		Preconditions.checkArgument(AccountConfig.supportedCurrencies.contains(request.getCurrency()), msg);

	}

	public static void validateAccount(CreateAccountRequest request) {

		Set<String> requestCurrencies = request.getCurrencies().stream().collect(Collectors.toSet());

		String msg = "Supported currencies are: " + String.join(", ", AccountConfig.supportedCurrencies);
		Preconditions.checkArgument(AccountConfig.supportedCurrencies.containsAll(requestCurrencies), msg);

	}
}
