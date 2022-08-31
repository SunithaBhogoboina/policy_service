package com.embea.policy.services.policy_service.infrastructure.persistence;

import com.embea.policy.services.policy_service.domain.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PersonRepositoryTests {

    @Autowired
    PersonRepository personRepository;

    @Test
    public void testCreatePerson() {
        Person person = new Person();

        person.setId(1);
        person.setFirstName("Jane");
        person.setSecondName("Johnson");
        person.setPremium(12.90);

        List<Person> personList = new ArrayList<>();
        personList.add(person);

        personRepository.save(person);
        Iterable<Person> personResult = personRepository.findAll();

        assertEquals("Jane", personResult.iterator().next().getFirstName());
        assertEquals("Johnson", personResult.iterator().next().getSecondName());
        assertEquals(12.90, personResult.iterator().next().getPremium());
    }
}
