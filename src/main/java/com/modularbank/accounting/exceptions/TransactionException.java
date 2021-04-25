package com.modularbank.accounting.exceptions;

public class TransactionException extends Exception {

	public TransactionException(String errorMessage) {
		super(errorMessage);
	}
}
