package com.embea.policy.services.policy_service.application;

import com.embea.policy.services.policy_service.domain.Person;
import com.embea.policy.services.policy_service.domain.Policy;
import com.embea.policy.services.policy_service.exception.ErrorResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
public class PolicyServiceTests {

    @Autowired
    private PolicyServiceImpl policyService;

    private Policy policy;

    @BeforeAll
    void generatePolicyObject() {
        policy = new Policy();
        Person person = new Person();
        person.setFirstName("Jane");
        person.setSecondName("Johnson");
        person.setPremium(12.90);

        List<Person> personList = new ArrayList<>();
        personList.add(person);
        policy.setInsuredPersons(personList);

        Date currentDate = new Date();
        Date futureDate = new Date(currentDate.getTime() + (1000*60*60*24));
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        policy.setStartDate(dateFormat.format(futureDate));
    }

    @Test
    void testPolicyIdIsNotNull() throws Exception {
        Optional<Policy> policyResult = policyService.createPolicy(policy);

        assertNotNull(policyResult.get().getPolicyId());
        assertEquals(policy.getStartDate(), policyResult.get().getStartDate());
    }

    @Test
    void testModifyPolicy() throws Exception {
        Person person = new Person();
        person.setFirstName("Will");
        person.setSecondName("Smith");
        person.setPremium(12.90);

        List<Person> insuredPersons = policy.getInsuredPersons();
        insuredPersons.add(person);

        policy.setInsuredPersons(insuredPersons);
        policy.setEffectiveDate(policy.getStartDate());

        Optional<Policy> policyResult = policyService.modifyPolicy(policy);
        List<Person> personList = policyResult.get().getInsuredPersons();

        assertEquals("Will", personList.get(1).getFirstName());
    }

    @Test
    void testModifyPolicyException() throws IOException, ParseException, ErrorResponse {
        Person person = new Person();
        person.setFirstName("Will");
        person.setSecondName("Smith");
        person.setPremium(12.90);

        List<Person> insuredPersons = policy.getInsuredPersons();
        insuredPersons.add(person);
        policy.setEffectiveDate("27.08.2022");
        policy.setInsuredPersons(insuredPersons);

        Exception ex = assertThrows(Exception.class, () -> policyService.modifyPolicy(policy), "Expected modifyPolicy to throw, but it didn't");
        assertTrue(ex.getMessage().contains("Policy effectiveDate should be a future date"));
    }
}
