package com.retail.model;

import java.time.LocalDate;

import jakarta.persistence.*;

@Entity
public class CustomerTransaction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String customer;
	private double amount;
	private LocalDate date;
	
	public CustomerTransaction() {
    }


	public CustomerTransaction(Long id, String customer, double amount, LocalDate date) {
		super();
		this.id = id;
		this.customer = customer;
		this.amount = amount;
		this.date = date;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "CustomerTransaction [id=" + id + ", customer=" + customer + ", amount=" + amount + ", date=" + date
				+ "]";
	}

}
