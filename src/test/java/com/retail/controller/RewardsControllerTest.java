package com.retail.controller;

import com.retail.dto.CustomerMonthlyPoints;
import com.retail.dto.CustomerPoints;
import com.retail.dto.CustomerRewards;
import com.retail.service.RewardsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class RewardsControllerTest {

    @Mock
    private RewardsService rewardsService;

    @InjectMocks
    private RetailController retailController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetRewards() {
        List<CustomerRewards> mockRewards = List.of(
            new CustomerRewards("Rahul", Map.of("JANUARY", 120), 120)
        );

        when(rewardsService.calculateRewards()).thenReturn(mockRewards);

        ResponseEntity<List<CustomerRewards>> response = retailController.getRewards();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockRewards, response.getBody());
    }

    @Test
    public void testGetCustomerPoints() {
        String customer = "Rahul";
        CustomerPoints dto = new CustomerPoints(customer, 300);

        when(rewardsService.getCustomerPointsResponse(customer)).thenReturn(dto);

        CustomerPoints result = retailController.getCustomerPoints(customer);

        assertEquals(dto.getCustomerName(), result.getCustomerName());
        assertEquals(dto.getTotalPoints(), result.getTotalPoints());
    }

    @Test
    public void testGetCustomerMonthlyPoints() {
        String customer = "Ravi";
        LocalDate from = LocalDate.of(2024, 1, 1);
        LocalDate to = LocalDate.of(2024, 3, 31);

        CustomerMonthlyPoints dto = new CustomerMonthlyPoints(customer, Map.of("JANUARY", 100), 100);

        when(rewardsService.getCustomerMonthlyPoints(customer, from, to)).thenReturn(dto);

        CustomerMonthlyPoints result = retailController.getCustomerMonthlyPoints(customer, from, to);

        assertEquals(dto.getCustomerName(), result.getCustomerName());
        assertEquals(dto.getTotalPoints(), result.getTotalPoints());
        assertEquals(dto.getMonthlyPoints(), result.getMonthlyPoints());
    }
}