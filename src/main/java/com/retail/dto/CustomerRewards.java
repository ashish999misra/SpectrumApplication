package com.retail.dto;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "customerName", "monthlyPoints", "totalPoints" })
public class CustomerRewards {
	private String customerName;
	private Map<String, Integer> monthlyPoints;
	private int totalPoints;

	public CustomerRewards(String customer, Map<String, Integer> monthlyPoints, int totalPoints) {
		this.customerName = customer;
		this.monthlyPoints = monthlyPoints;
		this.totalPoints = totalPoints;
	}

	public String getCustomerName() {
		return customerName;
	}

	public Map<String, Integer> getMonthlyPoints() {
		return monthlyPoints;
	}

	public int getTotalPoints() {
		return totalPoints;
	}

}
