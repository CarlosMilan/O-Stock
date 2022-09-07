package com.mm.optimagrowth.organization.repository;

import com.mm.optimagrowth.organization.model.Organization;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrganizationRepository extends CrudRepository<Organization, Long> {
    public Optional<Organization> findById(String organizationId);
}
