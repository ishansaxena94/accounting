package com.modularbank.accounting.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.modularbank.accounting.model.Person;

@Repository
public interface AccountsRepository extends JpaRepository<Person, Long> {
	List<Person> findByFirstName(String FirstName);

	@Override
	List<Person> findAll();
}
