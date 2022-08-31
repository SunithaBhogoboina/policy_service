package com.embea.policy.services.policy_service.infrastructure.persistence;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.embea.policy.services.policy_service.domain.Policy;

import java.util.Optional;

@Repository
public interface PolicyRepository extends CrudRepository<Policy, String> {
    @Query(value = "SELECT p FROM Policy p where p.policyId = ?1 and (p.startDate <= ?2 or p.effectiveDate <= ?2)")
    Optional<Policy> findByPolicyIdAndRequestDate(String policyId, String requestDate);
}