package com.embea.policy.services.policy_service.infrastructure.persistence;

import com.embea.policy.services.policy_service.domain.Person;
import com.embea.policy.services.policy_service.domain.Policy;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PolicyRepositoryTests {

    @Autowired
    PolicyRepository policyRepository;

    @Test
    public void testCreatePolicy() {
        Policy policy = new Policy();
        Person person = new Person();

        person.setFirstName("Jane");
        person.setSecondName("Johnson");
        person.setPremium(12.90);

        List<Person> personList = new ArrayList<>();
        personList.add(person);
        policy.setInsuredPersons(personList);

        Date currentDate = new Date();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        calendar.setTime(currentDate);
        calendar.add(Calendar.DATE, 1);
        policy.setStartDate(dateFormat.format(currentDate));

        policy.setPolicyId("CU423DF89");

        policyRepository.save(policy);
        Iterable<Policy> policyResult = policyRepository.findAll();

        assertEquals("CU423DF89", policyResult.iterator().next().getPolicyId());
        assertEquals(policy.getStartDate(), policyResult.iterator().next().getStartDate());
        assertEquals(1, policyResult.iterator().next().getInsuredPersons().size());
    }
}
