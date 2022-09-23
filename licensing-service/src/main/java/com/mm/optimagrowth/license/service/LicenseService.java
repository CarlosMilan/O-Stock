package com.mm.optimagrowth.license.service;

import com.mm.optimagrowth.license.model.License;

import java.util.Locale;

public interface LicenseService {

    License getLicense(String licenseId, String organizationId);
    License getLicense(String licenseId, String organizationId, String clientType);
    License createLicense(License license);
    License updateLicense(License license);
    String deleteLicense(String licenseId);
}
