package com.retail.service;

import com.retail.dto.CustomerMonthlyPoints;
import com.retail.dto.CustomerPoints;
import com.retail.dto.CustomerRewards;
import com.retail.exception.TransactionNotFoundException;
import com.retail.model.CustomerTransaction;
import com.retail.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RewardsServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private RewardsService rewardsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCalculateRewards_Success() {
        CustomerTransaction tx1 = new CustomerTransaction(null,"Ram",120.0,  LocalDate.of(2024, 3, 10));
        CustomerTransaction tx2 = new CustomerTransaction(null,"Ram", 80.0, LocalDate.of(2024, 3, 15));
        when(transactionRepository.findAll()).thenReturn(List.of(tx1, tx2));

        List<CustomerRewards> rewards = rewardsService.calculateRewards();

        assertEquals(1, rewards.size());
        CustomerRewards reward = rewards.get(0);
        assertEquals("Ram", reward.getCustomerName());
        assertTrue(reward.getMonthlyPoints().containsKey("MARCH"));
        assertEquals(reward.getTotalPoints(), reward.getMonthlyPoints().get("MARCH"));
    }

    @Test
    void testCalculateRewards_NoTransactions_ShouldThrowException() {
        when(transactionRepository.findAll()).thenReturn(List.of());
        assertThrows(TransactionNotFoundException.class, () -> rewardsService.calculateRewards());
    }
    
    @Test
    void testCalculateRewards_AmountExactly50_ShouldGet0Points() {
        CustomerTransaction tx = new CustomerTransaction(null, "Ram", 50.0, LocalDate.of(2024, 3, 10));
        when(transactionRepository.findAll()).thenReturn(List.of(tx));

        List<CustomerRewards> rewards = rewardsService.calculateRewards();

        assertEquals(1, rewards.size());
        assertEquals(0, rewards.get(0).getTotalPoints());
    }
    
    @Test
    void testCalculateRewards_AmountBetween50And100_ShouldGetCorrectPoints() {
        CustomerTransaction tx = new CustomerTransaction(null, "Ravi", 70.0, LocalDate.of(2024, 4, 5));
        when(transactionRepository.findAll()).thenReturn(List.of(tx));

        List<CustomerRewards> rewards = rewardsService.calculateRewards();

        assertEquals(1, rewards.size());
        assertEquals(20, rewards.get(0).getTotalPoints());
    }
    
    @Test
    void testCalculateRewards_AmountOver100_ShouldGetDoublePointsAbove100() {
        CustomerTransaction tx = new CustomerTransaction(null, "Rahul", 120.0, LocalDate.of(2024, 5, 1));
        when(transactionRepository.findAll()).thenReturn(List.of(tx));

        List<CustomerRewards> rewards = rewardsService.calculateRewards();

        assertEquals(1, rewards.size());
        assertEquals(90, rewards.get(0).getTotalPoints());
    }
    
    @Test
    void testCalculateRewards_MultipleCustomersAndMonths() {
        CustomerTransaction tx1 = new CustomerTransaction(null, "Amit", 110.0, LocalDate.of(2024, 3, 15)); 
        CustomerTransaction tx2 = new CustomerTransaction(null, "Amit", 60.0, LocalDate.of(2024, 4, 15));  
        CustomerTransaction tx3 = new CustomerTransaction(null, "Neha", 130.0, LocalDate.of(2024, 3, 20)); 
        when(transactionRepository.findAll()).thenReturn(List.of(tx1, tx2, tx3));

        List<CustomerRewards> rewards = rewardsService.calculateRewards();

        assertEquals(2, rewards.size());

        CustomerRewards amitRewards = rewards.stream().filter(r -> r.getCustomerName().equals("Amit")).findFirst().orElseThrow();
        assertEquals(80, amitRewards.getTotalPoints());
        assertEquals(2, amitRewards.getMonthlyPoints().size()); 

        CustomerRewards nehaRewards = rewards.stream().filter(r -> r.getCustomerName().equals("Neha")).findFirst().orElseThrow();
        assertEquals(110, nehaRewards.getTotalPoints());
    }
    
    @Test
    void testCalculateRewards_NegativeAmount_ShouldGetZeroPoints() {
        CustomerTransaction tx = new CustomerTransaction(null, "Ravi", -100.0, LocalDate.of(2024, 3, 10));
        when(transactionRepository.findAll()).thenReturn(List.of(tx));

        List<CustomerRewards> rewards = rewardsService.calculateRewards();

        assertEquals(1, rewards.size());
        assertEquals(0, rewards.get(0).getTotalPoints());
    }

    @Test
    void testGetCustomerPointsResponse_Success() {
        CustomerTransaction tx1 = new CustomerTransaction(null,"Rahul", 130, LocalDate.of(2024, 4, 5));
        when(transactionRepository.findByCustomer("Rahul")).thenReturn(List.of(tx1));

        CustomerPoints result = rewardsService.getCustomerPointsResponse("Rahul");
        assertEquals("Rahul", result.getCustomerName());
        assertTrue(result.getTotalPoints() > 0);
    }

    @Test
    void testGetCustomerPointsResponse_CustomerNotFound_ShouldThrowException() {
        when(transactionRepository.findByCustomer("Ravi")).thenReturn(List.of());
        assertThrows(TransactionNotFoundException.class, () -> rewardsService.getCustomerPointsResponse("Ravi"));
    }

    @Test
    void testGetCustomerMonthlyPointsDTO_Success() {
        CustomerTransaction tx1 = new CustomerTransaction(null,"Akash", 110,LocalDate.of(2024, 2, 20));
        when(transactionRepository.findByCustomerAndDateBetween(eq("Akash"), any(), any()))
                .thenReturn(List.of(tx1));

        CustomerMonthlyPoints result = rewardsService.getCustomerMonthlyPoints("Akash",
                LocalDate.of(2024, 2, 1), LocalDate.of(2024, 2, 28));

        assertEquals("Akash", result.getCustomerName());
        assertEquals(result.getTotalPoints(), result.getMonthlyPoints().get("FEBRUARY"));
    }

    @Test
    void testGetCustomerMonthlyPointsDTO_NoData_ShouldThrowException() {
        when(transactionRepository.findByCustomerAndDateBetween(eq("Sai"), any(), any()))
                .thenReturn(List.of());

        assertThrows(TransactionNotFoundException.class, () ->
                rewardsService.getCustomerMonthlyPoints("Sai", LocalDate.now().minusMonths(1), LocalDate.now()));
    }
}
