package com.retail.service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.retail.dto.CustomerMonthlyPoints;
import com.retail.dto.CustomerPoints;
import com.retail.dto.CustomerRewards;
import com.retail.exception.TransactionNotFoundException;
import com.retail.model.CustomerReward;
import com.retail.model.CustomerTransaction;
import com.retail.repository.TransactionRepository;


//Service class responsible for calculating reward points based on customer transactions.
@Service
public class RewardsService {

	private static final Logger logger = LoggerFactory.getLogger(RewardsService.class);

	private final TransactionRepository transactionRepository;

	public RewardsService(TransactionRepository transactionRepository) {
		this.transactionRepository = transactionRepository;
	}

	// Calculates monthly reward points for all customers.
	public List<CustomerRewards> calculateRewards() {
		logger.info("Calculating rewards for all customers");
		List<CustomerTransaction> transactions = transactionRepository.findAll();

		if (transactions.isEmpty()) {
			logger.warn("No transactions found in the system");
			throw new TransactionNotFoundException("No transactions found in the system.");
		}

		Map<String, CustomerReward> customerRewards = new HashMap<>();

		for (CustomerTransaction t : transactions) {
			int points = calculatePoints(t.getAmount());
			String customer = t.getCustomer();
			String month = t.getDate().getMonth().toString();

			customerRewards.putIfAbsent(customer, new CustomerReward(customer));
			customerRewards.get(customer).addPoints(month, points);

		}

		logger.info("Rewards calculation completed");
		return customerRewards.values().stream()
				.map(r -> new CustomerRewards(r.getCustomer(), r.getMonthlyPoints(), r.getTotalPoints()))
				.collect(Collectors.toList());
	}

	private int calculatePoints(double amount) {
		int points = 0;
		if (amount > 100) {
			points += (int) ((amount - 100) * 2 + 50);
		} else if (amount > 50) {
			points += (int) (amount - 50);
		}
		return points;
	}

	// Returns the total reward points for a specific customer.
	public CustomerPoints getCustomerPointsResponse(String customer) {
		logger.info("Fetching total reward points for customer: {}", customer);
		List<CustomerTransaction> transactions = transactionRepository.findByCustomer(customer);

		if (transactions.isEmpty()) {
			logger.warn("Customer not found: {}", customer);
			throw new TransactionNotFoundException("Customer '" + customer + "' not found.");
		}

		int totalPoints = 0;

		for (CustomerTransaction tx : transactions) {
			double amount = tx.getAmount();
			int points = calculatePoints(amount);

			totalPoints += points;
		}

		return new CustomerPoints(customer, totalPoints);
	}

	// Returns monthly reward data for a specific customer within a date range.
	public CustomerReward getMonthlyPoints(String customer, LocalDate from, LocalDate to) {
		logger.info("Fetching monthly points for customer '{}' from {} to {}", customer, from, to);

		List<CustomerTransaction> transactions = transactionRepository.findByCustomerAndDateBetween(customer, from, to);

		if (transactions.isEmpty()) {
			logger.warn("No transactions found for customer '{}' in the given range", customer);

			throw new TransactionNotFoundException(
					"No transactions found for customer '" + customer + "' in the given range.");
		}

		CustomerReward customerReward = new CustomerReward(customer);

		for (CustomerTransaction tx : transactions) {
			int points = calculatePoints(tx.getAmount());
			String month = tx.getDate().getMonth().toString();

			customerReward.addPoints(month, points);
		}

		return customerReward;
	}

	public CustomerMonthlyPoints getCustomerMonthlyPoints(String customer, LocalDate from, LocalDate to) {
		logger.info("Generating CustomerMonthlyPoints for customer '{}'", customer);

		CustomerReward reward = getMonthlyPoints(customer, from, to);

		return new CustomerMonthlyPoints(reward.getCustomer(), reward.getMonthlyPoints(), reward.getTotalPoints());
	}

}
