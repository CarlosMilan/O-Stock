package com.mm.optimagrowth.license.service;

import com.mm.optimagrowth.license.config.ServiceConfig;
import com.mm.optimagrowth.license.model.License;
import com.mm.optimagrowth.license.model.Organization;
import com.mm.optimagrowth.license.repository.LicenseRepository;
import com.mm.optimagrowth.license.service.client.OrganizationDiscoveryClient;
import com.mm.optimagrowth.license.service.client.OrganizationRestTemplateClient;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

@Service
public class LicenseServiceImpl implements LicenseService {

    @Autowired
    private MessageSource messages;

    @Autowired
    private LicenseRepository licenseRepository;

    @Autowired
    private OrganizationDiscoveryClient organizationDiscoveryClient;

    @Autowired
    private OrganizationRestTemplateClient organizationRestTemplateClient;

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
    public License getLicense(String licenseId, String organizationId, String clientType) {
        License license = licenseRepository.findByOrganizationIdAndLicenseId(organizationId, licenseId);

        if (null == license) {
            throw new IllegalArgumentException(String.format(
                    messages.getMessage("license.search.error.message", null, null), licenseId, organizationId));
        }
        Organization organization = retriveOrganizationInfo(organizationId, clientType);

        if (null != organization) {
            license.setOrganizationName(organization.getName());
            license.setContactName(organization.getContactName());
            license.setContactEmail(organization.getContactName());
            license.setContactPhone(organization.getContactPhone());
        }

        return license.withComment(config.getProperty());
    }

    @CircuitBreaker(name = "licenseService", fallbackMethod = "buildFallbackLicenseList")
    public List<License> getLicensesByOrganization(String organizationId) throws TimeoutException {
        randomlyRunLong(); // Este metodo simula un posible problema en el acceso a la base de datos
        return licenseRepository.findByOrganizationId(organizationId);
    }

    private List<License> buildFallbackLicenseList(String organizationId, Throwable t) {
        List<License> fallbackList = new ArrayList<>();
        License license = new License();
        license.setLicenseId("0000000-00-00000");
        license.setOrganizationId(organizationId);
        license.setProductName("Sorry no licensing information currently available");
        fallbackList.add(license);
        return fallbackList;
    }

    private void randomlyRunLong() throws TimeoutException {
        Random rand = new Random();
        int randomNum = rand.nextInt(3) + 1;
        if (randomNum == 3) sleep();
    }

    private void sleep() throws TimeoutException {
        try {
            Thread.sleep(5000);
            throw new java.util.concurrent.TimeoutException();
        } catch (InterruptedException e) {
            //TODO: agregar lombok con sl4j para colocar aca un log.error(e.getMessage())
            System.out.println("Ocurrió una excepcion");
        }
    }

    private Organization retriveOrganizationInfo(String organizationId, String clientType) {
        Organization organization = null;

        switch (clientType) {
            case "feign": break;
            case "rest" : {
                System.out.println("I am using the RestTemplate client");
                organization = getOrganization(organizationId);
                break;
            }
            case "discovery": {
                System.out.println("I am using the discovery client");
                organization = organizationDiscoveryClient.getOrganization(organizationId);
                break;
                }
            default: {
                break;
            }
        }

        return organization;
    }

    @CircuitBreaker(name = "organizationService")
    private Organization getOrganization(String organizationId) {
        return organizationRestTemplateClient.getOrganization(organizationId);
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
