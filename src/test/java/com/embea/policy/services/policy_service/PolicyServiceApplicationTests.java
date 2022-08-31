package com.embea.policy.services.policy_service;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.embea.policy.services.policy_service.presentation.PolicyController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PolicyServiceApplicationTests {

	@Autowired
	PolicyController policyController;

	@Test
	void contextLoads() {
		assertNotNull(policyController);
	}

}
