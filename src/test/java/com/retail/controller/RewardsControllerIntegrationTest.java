package com.retail.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.retail.service.RewardsService;

@WebMvcTest(RetailController.class)
public class RewardsControllerIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private RewardsService rewardsService;

	@Test
	void testGetRewardsEndpoint() throws Exception {		
		org.mockito.Mockito.when(rewardsService.calculateRewards()).thenReturn(Collections.emptyMap());

		mockMvc.perform(get("/api/v1/rewards")).andExpect(status().isOk());
	}
	
	 @Test
	    void testGetCustomerPoints_validCustomer_returnsTotalPoints() throws Exception {
	       
	        String customer = "Rahul";
	        int totalPoints = 120;

	        Mockito.when(rewardsService.calculateTotalPoints(customer)).thenReturn(totalPoints);

	       
	        mockMvc.perform(get("/api/v1/customerTotalPoints")
	                .param("customer", customer))
	                .andExpect(status().isOk())
	                .andExpect(jsonPath("$.customer").value(customer))
	                .andExpect(jsonPath("$.totalPoints").value(totalPoints));
	    }

}
