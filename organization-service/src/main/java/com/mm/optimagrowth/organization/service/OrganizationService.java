package com.mm.optimagrowth.organization.service;

import com.mm.optimagrowth.organization.model.Organization;
import com.mm.optimagrowth.organization.repository.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrganizationService {

    @Autowired
    private OrganizationRepository organizationRepository;

    public Organization findById(String organizationId) {
        return organizationRepository.findById(organizationId).orElse(null);
    }

    public Organization create(Organization organization) {
        return organizationRepository.save(organization);
    }

    public void update(Organization organization){
        organizationRepository.save(organization);
    }

    public void delete(Organization organization) {
        organizationRepository.deleteById(organization.getId());
    }

}
