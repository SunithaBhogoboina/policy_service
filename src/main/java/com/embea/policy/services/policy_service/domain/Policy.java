package com.embea.policy.services.policy_service.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Table(name = "policy")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Policy {
    @Id
    private String policyId;
    private String startDate;
    private String effectiveDate;
    private String requestDate;
    @ElementCollection
    @OneToMany(cascade = CascadeType.ALL)
    private List<Person> insuredPersons;
    private double totalPremium;
}