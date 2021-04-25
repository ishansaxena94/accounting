package com.modularbank.accounting.requests;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class CreateAccountRequest {

	Long customerId;

	String country;

	List<String> currencies;
}
