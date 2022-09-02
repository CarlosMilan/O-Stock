package com.mm.optimagrowth.license.service;

import com.mm.optimagrowth.license.config.ServiceConfig;
import com.mm.optimagrowth.license.model.License;
import com.mm.optimagrowth.license.repository.LicenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Random;
import java.util.UUID;

@Service(value = "dummyLicenseService")
public class LicenseServiceImpl implements LicenseService {

    @Autowired
    private MessageSource messages;

    @Autowired
    private LicenseRepository licenseRepository;

    @Autowired
    private ServiceConfig config;
    @Override
    public License getLicense(String licenseId, String organizationId) {
        License license = licenseRepository.findByOrganizationIdAndLicenseId(organizationId, licenseId);
        if (license == null) {
            throw new IllegalArgumentException(
                    String.format(messages.getMessage("license.search.error.message", null, null), licenseId, organizationId)
            );
        }
        return license.withComment(config.getProperty());
    }

    @Override
    public License createLicense(License license) {
        license.setLicenseId(UUID.randomUUID().toString());
        licenseRepository.save(license);
        return license.withComment(config.getProperty());
    }

    @Override
    public License updateLicense(License license) {
        licenseRepository.save(license);
        return license.withComment(config.getProperty());
    }

    @Override
    public String deleteLicense(String licenseId) {
        String responseMessage = null;
        License license = new License();
        license.setLicenseId(licenseId);
        licenseRepository.delete(license);
        responseMessage = String.format(messages.getMessage("license.delete.message", null, null), licenseId);
        return responseMessage;
    }
}
