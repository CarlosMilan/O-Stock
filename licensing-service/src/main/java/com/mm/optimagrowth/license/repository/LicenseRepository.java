package com.mm.optimagrowth.license.repository;

import com.mm.optimagrowth.license.model.License;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LicenseRepository extends CrudRepository<License, String> {
    List<License> findByOranizationId(String organizationId);
    License findByOrganizationIdAndLicenseId(String organizationId, String licenseId);
}
