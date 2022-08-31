package com.embea.policy.services.policy_service.application;

import com.embea.policy.services.policy_service.domain.Policy;

import java.util.Optional;

public interface PolicyService {

    Optional<Policy> createPolicy(Policy policy) throws Exception;
    Optional<Policy> modifyPolicy(Policy policy) throws Exception;
    Optional<Policy> fetchPolicy(Policy policy) throws Exception;
    Iterable<Policy> fetchPolicies();
}
