package com.retail.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.retail.model.CustomerTransaction;

public interface TransactionRepository extends JpaRepository<CustomerTransaction, Long> {

	List<CustomerTransaction> findByCustomer(String customer);

	List<CustomerTransaction> findByCustomerAndDateBetween(String customer, LocalDate from, LocalDate to);

}
