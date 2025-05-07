package com.retail.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.retail.model.CustomerReward;
import com.retail.model.CustomerTransaction;
import com.retail.repository.TransactionRepository;

@Service
public class RewardsService {

	private final TransactionRepository transactionRepository;

	public RewardsService(TransactionRepository transactionRepository) {
		this.transactionRepository = transactionRepository;
	}

	public Map<String, CustomerReward> calculateRewards() {
		List<CustomerTransaction> transactions = transactionRepository.findAll();
		Map<String, CustomerReward> customerRewards = new HashMap<>();

		for (CustomerTransaction t : transactions) {
			int points = calculatePoints(t.getAmount());
			String customer = t.getCustomer();
			String month = t.getDate().getMonth().toString();

			customerRewards.putIfAbsent(customer, new CustomerReward(customer));
			customerRewards.get(customer).addPoints(month, points);

		}

		return customerRewards;
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

	public int calculateTotalPoints(String customer) {
		List<CustomerTransaction> transactions = transactionRepository.findByCustomer(customer);
		int totalPoints = 0;

		for (CustomerTransaction tx : transactions) {
			double amount = tx.getAmount();
			int points = calculatePoints(amount);

			totalPoints += points;
		}

		return totalPoints;
	}

}
