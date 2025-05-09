package com.retail.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "customerName", "totalPoints" })
public class CustomerPoints {

	private String customerName;
	private int totalPoints;

	public CustomerPoints(String customer, int totalPoints) {
		super();
		this.customerName = customer;
		this.totalPoints = totalPoints;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customer) {
		this.customerName = customer;
	}

	public int getTotalPoints() {
		return totalPoints;
	}

	public void setTotalPoints(int totalPoints) {
		this.totalPoints = totalPoints;
	}

}
