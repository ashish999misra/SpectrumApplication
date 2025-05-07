package com.retail.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.retail.model.CustomerReward;
import com.retail.service.RewardsService;

@RequestMapping("/api/v1")
@RestController
public class RetailController {

	@Autowired
	private RewardsService rewardsService;

	@GetMapping("/rewards")
	public Map<String, CustomerReward> getRewards() {
		return rewardsService.calculateRewards();
	}

	@GetMapping("/customerTotalPoints")
	public Map<String, Object> getCustomerPoints(@RequestParam String customer) {
		int points = rewardsService.calculateTotalPoints(customer);

		Map<String, Object> response = new LinkedHashMap<>();
		response.put("customer", customer);
		response.put("totalPoints", points);

		return response;
	}

}
