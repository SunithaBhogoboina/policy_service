package com.embea.policy.services.policy_service.application;

import com.embea.policy.services.policy_service.domain.Person;
import com.embea.policy.services.policy_service.infrastructure.persistence.PolicyRepository;
import com.embea.policy.services.policy_service.domain.Policy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.Date;

@Slf4j
@Service
public class PolicyServiceImpl implements PolicyService {

    @Autowired
    private PolicyRepository policyRepository;

    @Value("${date.format}")
    private String dateFormat;

    @Value("${policy.id.length}")
    private int policyIdLength;

    @Value("${policy.id.generator.seed}")
    private String policyIdGeneratorSeed;

    private static final SecureRandom secureRandom = new SecureRandom();

    public Optional<Policy> createPolicy(Policy policy) throws Exception {
        if(policy.getInsuredPersons().size() > 0) {
            if(isDateFuture(policy.getStartDate(), dateFormat)) {
                savePolicy(policy);
                log.info("Policy successfully created", policy);
            } else {
                log.error("Policy startDate is not a future date", policy.getStartDate());
                throw new Exception("Policy startDate should be a future date") ;
            }
        } else {
            log.error("Insured persons data is not added in payload", policy);
            throw new Exception("Please add Insured persons data") ;
        }
        log.info("Successful execution of request");

        return policyRepository.findById(policy.getPolicyId());
    }

    public Optional<Policy> modifyPolicy(Policy policy) throws Exception {
        if(policy.getInsuredPersons().size() > 0) {
            if (isDateFuture(policy.getEffectiveDate(), dateFormat)) {
                savePolicy(policy);
                log.info("Policy successfully modified", policy);
            } else {
                log.error("Policy effectiveDate is not a future date", policy.getEffectiveDate());
                throw new Exception("Policy effectiveDate should be a future date");
            }
        } else {
            log.error("Insured persons data is not added in payload", policy);
            throw new Exception("Please add Insured persons data") ;
        }
        log.info("Successful execution of request");

        return policyRepository.findById(policy.getPolicyId());
    }

    public Optional<Policy> fetchPolicy(Policy policy) throws Exception {
        if(isNullOrEmpty(policy.getRequestDate())) {
            Date currentDate = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
            policy.setRequestDate(simpleDateFormat.format(currentDate));
        }

        return policyRepository.findByPolicyIdAndRequestDate(policy.getPolicyId(), policy.getRequestDate());
    }

    public Iterable<Policy> fetchPolicies() {
        return policyRepository.findAll();
    }

    public static boolean isDateFuture(final String date, final String dateFormat) throws ParseException {
        LocalDate localDate = LocalDate.now(ZoneId.systemDefault());
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(dateFormat);
        LocalDate inputDate = LocalDate.parse(date, dtf);

        return inputDate.isAfter(localDate);
    }

    public void savePolicy(Policy policy) {
        generatePolicyObject(policy);
        policyRepository.save(policy);
    }
    
    private Policy generatePolicyObject(Policy policy) {
        double totalPremium = 0.0;
        for(Person person: policy.getInsuredPersons()) {
            totalPremium += person.getPremium();
        }

        policy.setTotalPremium(totalPremium);
        if(isNullOrEmpty(policy.getPolicyId())) {
            policy.setPolicyId(generatePolicyId(policyIdLength));
        }

        return policy;
    }

    private String generatePolicyId(final int len){
        StringBuilder sb = new StringBuilder(len);
        for(int i = 0; i < len; i++)
            sb.append(policyIdGeneratorSeed.charAt(secureRandom.nextInt(policyIdGeneratorSeed.length())));

        return sb.toString();
    }

    private static boolean isNullOrEmpty(final String target) {
        return target == null || target.isEmpty();
    }
}