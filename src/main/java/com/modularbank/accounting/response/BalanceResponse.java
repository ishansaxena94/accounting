package com.modularbank.accounting.response;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class BalanceResponse {

	Double amount;
	String currency;
}
