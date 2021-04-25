package com.modularbank.accounting.response;

import java.util.List;

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
public class CreateAccountResponse {

	Long accountId;
	Long customerId;

	List<BalanceResponse> balances;
}
