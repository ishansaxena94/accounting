package com.modularbank.accounting.validators;

import java.util.Set;
import java.util.stream.Collectors;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.modularbank.accounting.configurations.AccountConfig;
import com.modularbank.accounting.requests.CreateAccountRequest;

public class AccountValidator {

	public static void validateAccount(CreateAccountRequest request) {

		Preconditions.checkNotNull(request.getCustomerId(), "CustomerId field is missing");
		Preconditions.checkArgument(request.getCustomerId() > 0, "CustomerId should be greater than 0");
		Preconditions.checkArgument(!Strings.isNullOrEmpty(request.getCountry()), "Country is empty");

		Set<String> requestCurrencies = request.getCurrencies().stream().collect(Collectors.toSet());

		String msg = "Supported currencies are: " + String.join(", ", AccountConfig.supportedCurrencies);
		Preconditions.checkArgument(AccountConfig.supportedCurrencies.containsAll(requestCurrencies), msg);
	}
}
