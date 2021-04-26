package com.modularbank.accounting.exceptions;

public class AccountException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4950269680310510701L;

	public AccountException(String errorMessage) {
		super(errorMessage);
	}

}
