package com.retail.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.retail.model.CustomerReward;
import com.retail.model.CustomerTransaction;
import com.retail.repository.TransactionRepository;

@ExtendWith(MockitoExtension.class)
public class RewardsServiceTest {
	@Mock
	private TransactionRepository transactionRepository;
	@InjectMocks
	private RewardsService rewardsService;

	@BeforeEach
	public void setup() {
	    transactionRepository = mock(TransactionRepository.class);
	    rewardsService = new RewardsService(transactionRepository); 
	}

	@Test
	public void testRewardCalculation() {
		List<CustomerTransaction> transactions = Arrays.asList(
				new CustomerTransaction(null, "Rahul", 120.0, LocalDate.of(2024, 1, 15)), 
				new CustomerTransaction(null, "Rahul", 75.0, LocalDate.of(2024, 1, 20)) 
		);

		when(transactionRepository.findAll()).thenReturn(transactions);

		Map<String, CustomerReward> rewards = rewardsService.calculateRewards();

		assertTrue(rewards.containsKey("Rahul"));
		CustomerReward reward = rewards.get("Rahul");
		assertEquals(115, reward.getTotalPoints()); 
		assertTrue(reward.getMonthlyPoints().containsKey("JANUARY"));
	}
	
	 @Test
	    void testCalculateTotalPoints_withMixedTransactions_returnsCorrectPoints() {
	        
	        String customer = "Rahul";

	        List<CustomerTransaction> transactions = List.of(
	                new CustomerTransaction(1L, customer, 120.0, LocalDate.now()), 
	                new CustomerTransaction(2L, customer, 75.0, LocalDate.now()),  
	                new CustomerTransaction(3L, customer, 45.0, LocalDate.now())  
	        );

	        Mockito.when(transactionRepository.findByCustomer(customer)).thenReturn(transactions);

	       
	        int totalPoints = rewardsService.calculateTotalPoints(customer);

	        
	        assertEquals(115, totalPoints);
	    }

	    @Test
	    void testCalculateTotalPoints_withNoTransactions_returnsZero() {
	        Mockito.when(transactionRepository.findByCustomer("Ravi")).thenReturn(List.of());
	        assertEquals(0, rewardsService.calculateTotalPoints("Ravi"));
	    }
}
