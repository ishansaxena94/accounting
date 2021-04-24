package com.modularbank.accounting.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "persons")
@Data
public class Person {

	@Column(name = "id")
	@Id
	long id;

	@Column(name = "lastname")
	String lastName;

	@Column(name = "firstname")
	String firstName;
}
