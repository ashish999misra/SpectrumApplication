package com.retail.model;

import java.util.HashMap;
import java.util.Map;

public class CustomerReward {
	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public void setMonthlyPoints(Map<String, Integer> monthlyPoints) {
		this.monthlyPoints = monthlyPoints;
	}

	private String customer;
    private Map<String, Integer> monthlyPoints = new HashMap<>();

    public CustomerReward(String customer) {
        this.customer = customer;
    }

    public void addPoints(String month, int points) {
        monthlyPoints.put(month, monthlyPoints.getOrDefault(month, 0) + points);
    }

    public String getCustomer() {
        return customer;
    }

    public Map<String, Integer> getMonthlyPoints() {
        return monthlyPoints;
    }

    public int getTotalPoints() {
        return monthlyPoints.values().stream().mapToInt(Integer::intValue).sum();
    }

}
