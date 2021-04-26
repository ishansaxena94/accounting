package com.modularbank.accounting.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "balance")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Balance {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;

	Double amount;

	@Column(name = "currency_code")
	String currency;

	@Column(name = "account_id")
	Long accountId;
}
