package com.modularbank.accounting.exceptions;

public class InvalidAmountException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2647347328326619705L;

	public InvalidAmountException(String errorMessage) {
		super(errorMessage);
	}

}
