package com.mm.optimagrowth.license.service;

import com.mm.optimagrowth.license.model.License;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeoutException;

public interface LicenseService {

    License getLicense(String licenseId, String organizationId);
    License getLicense(String licenseId, String organizationId, String clientType);
    List<License> getLicensesByOrganization(String organizationId) throws TimeoutException;
    License createLicense(License license);
    License updateLicense(License license);
    String deleteLicense(String licenseId);
}
