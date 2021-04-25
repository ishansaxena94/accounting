package com.modularbank.accounting.response;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetTransactionResponse {

	Long accountId;
	Long transactionId;
	Double amount;
	String currency;
	String direction;
	String description;

}
