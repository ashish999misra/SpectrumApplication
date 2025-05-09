package com.retail.dto;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;


@JsonPropertyOrder({ "customerName", "monthlyPoints", "totalPoints" })
public class CustomerMonthlyPoints {

	private String customerName;
	private Map<String, Integer> monthlyPoints;
	private int totalPoints;

	public CustomerMonthlyPoints(String customer, Map<String, Integer> monthlyPoints, int totalPoints) {
		this.customerName = customer;
		this.monthlyPoints = monthlyPoints;
		this.totalPoints = totalPoints;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customer) {
		this.customerName = customer;
	}

	public Map<String, Integer> getMonthlyPoints() {
		return monthlyPoints;
	}

	public void setMonthlyPoints(Map<String, Integer> monthlyPoints) {
		this.monthlyPoints = monthlyPoints;
	}

	public int getTotalPoints() {
		return totalPoints;
	}

	public void setTotalPoints(int totalPoints) {
		this.totalPoints = totalPoints;
	}

}
