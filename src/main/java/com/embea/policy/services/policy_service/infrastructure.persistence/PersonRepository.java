package com.embea.policy.services.policy_service.infrastructure.persistence;

import com.embea.policy.services.policy_service.domain.Person;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends CrudRepository<Person, String> {

}