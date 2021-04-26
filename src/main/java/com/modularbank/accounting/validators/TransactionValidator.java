package com.modularbank.accounting.validators;

import com.google.common.base.Preconditions;
import com.modularbank.accounting.configurations.TransactionConfig;
import com.modularbank.accounting.requests.CreateTransactionRequest;

public class TransactionValidator {

	public static void validate(CreateTransactionRequest request) {

		Preconditions.checkNotNull(request.getAccountId(), "Account missing");
		Preconditions.checkArgument(request.getAccountId() > 0, "Account Id should be greatee than 0");

		Preconditions.checkArgument(request.getAmount() > 0, "Invalid amount");
		Preconditions.checkNotNull(request.getDescription(), "Description missing");

		Preconditions.checkArgument(TransactionConfig.transctionDirection.contains(request.getDirection()),
				"Invalid Direction");

		CurrencyValidator.validateTransaction(request);

	}
}
