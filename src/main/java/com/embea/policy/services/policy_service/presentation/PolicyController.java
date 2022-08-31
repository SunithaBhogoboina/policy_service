package com.embea.policy.services.policy_service.presentation;

import com.embea.policy.services.policy_service.application.PolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;

import com.embea.policy.services.policy_service.domain.Policy;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/policies")
public class PolicyController {

    @Autowired(required = false)
    private PolicyService policyService;

    @PostMapping(value = "/createPolicy", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Optional<Policy>> createPolicy(@RequestBody Policy policy) throws Exception {
        return ResponseEntity.ok(policyService.createPolicy(policy));
    }

    @PostMapping(value = "/modifyPolicy", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Optional<Policy>> modifyPolicy(@RequestBody Policy policy) throws Exception {
        return ResponseEntity.ok(policyService.modifyPolicy(policy));
    }

    @PostMapping(value = "/fetchPolicy", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Optional<Policy>> fetchPolicy(@RequestBody Policy policy) throws Exception {
        return ResponseEntity.ok(policyService.fetchPolicy(policy));
    }

    @GetMapping(value = "/fetchPolicies", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Iterable<Policy>> fetchPolicies() throws IOException {
        return ResponseEntity.ok(policyService.fetchPolicies());
    }
}