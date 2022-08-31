package com.embea.policy.services.policy_service.presentation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.embea.policy.services.policy_service.domain.Person;
import com.embea.policy.services.policy_service.domain.Policy;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest(classes = PolicyController.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PolicyControllerIntegrationTests {

    @LocalServerPort
    private String port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testCreatePolicy() {
        Person person = new Person();
        person.setFirstName("Jane");
        person.setSecondName("Johnson");
        person.setPremium(12.90);

        List<Person> personList = new ArrayList<>();
        personList.add(person);

        Policy policy = new Policy();
        policy.setStartDate("15.07.2022");
        policy.setInsuredPersons(personList);

        ResponseEntity<String> responseEntity = this.restTemplate.postForEntity("http://localhost:" + port + "/policies/createPolicy", policy, String.class);
        assertEquals("200 OK", responseEntity.getStatusCode().toString());
    }

    @Test
    public void testModifyPolicy() {
        Person person = new Person();
        person.setFirstName("Jane");
        person.setSecondName("Johnson");
        person.setPremium(12.90);

        List<Person> personList = new ArrayList<>();
        personList.add(person);

        Policy policy = new Policy();
        policy.setPolicyId("CU423DF89");
        policy.setEffectiveDate("15.09.2022");
        policy.setInsuredPersons(personList);

        ResponseEntity<String> responseEntity = this.restTemplate.postForEntity("http://localhost:" + port + "/policies/modifyPolicy", policy, String.class);
        assertEquals("200 OK", responseEntity.getStatusCode().toString());
    }

    @Test
    public void testFetchPolicy() {
        Policy policy = new Policy();
        policy.setPolicyId("CU423DF89");
        policy.setRequestDate("15.08.2022");

        ResponseEntity<String> responseEntity = this.restTemplate.postForEntity("http://localhost:" + port + "/policies/fetchPolicy", policy, String.class);
        assertEquals("200 OK", responseEntity.getStatusCode().toString());
    }

    @Test
    public void testFetchPolicies() {
        ResponseEntity<String> responseEntity = this.restTemplate.getForEntity("http://localhost:" + port + "/policies/fetchPolicies", String.class);
        assertEquals("200 OK", responseEntity.getStatusCode().toString());
    }

}
