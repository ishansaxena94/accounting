package com.modularbank.accounting.requests;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class CreateTransactionRequest {

	Long accountId;
	Double amount;
	String currency;
	String direction;
	String description;
}
