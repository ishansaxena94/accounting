package com.modularbank.accounting.configurations;

import java.util.HashSet;
import java.util.Set;

public class TransactionConfig {

	public static Set<String> transctionDirection = new HashSet<>();

	static {
		transctionDirection.add("IN");
		transctionDirection.add("OUT");
	}
}
