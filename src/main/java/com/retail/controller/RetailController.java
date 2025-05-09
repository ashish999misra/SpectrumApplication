package com.retail.controller;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.retail.dto.CustomerMonthlyPoints;
import com.retail.dto.CustomerPoints;
import com.retail.dto.CustomerRewards;
import com.retail.service.RewardsService;


//Rest controller for customer reward point operations.
@RequestMapping("/api/v1")
@RestController
public class RetailController {
	
	private static final Logger logger = LoggerFactory.getLogger(RetailController.class);

	@Autowired
	private RewardsService rewardsService;

	//Get reward points for all customers.
	@GetMapping("/rewards")
	public ResponseEntity<List<CustomerRewards>> getRewards() {
		logger.info("GET /rewards - Fetching rewards for all customers");
		List<CustomerRewards> rewards = rewardsService.calculateRewards();
		return ResponseEntity.ok(rewards);
	}

	//Get total reward points for a specific customer.
	@GetMapping("/customerTotalPoints")
	public CustomerPoints getCustomerPoints(@RequestParam String customer) {
		logger.info("GET /customerTotalPoints - customer: {}", customer);
		return rewardsService.getCustomerPointsResponse(customer);

	}

	//Get monthly reward points for a customer within a date range.
	@GetMapping("/customerMonthlyPoints")
	public CustomerMonthlyPoints getCustomerMonthlyPoints(@RequestParam String customer,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
		
		logger.info("GET /customerMonthlyPoints - customer: {}, from: {}, to: {}", customer, from, to);
		return rewardsService.getCustomerMonthlyPoints(customer, from, to);
	}

}
