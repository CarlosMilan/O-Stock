package com.mm.optimagrowth.license.controller;

import com.mm.optimagrowth.license.model.License;
import com.mm.optimagrowth.license.service.LicenseService;
import com.mm.optimagrowth.license.utils.UserContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeoutException;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "v1/organization/{organizationId}/license")
public class LicenseController {

    private static final Logger logger = LoggerFactory.getLogger(LicenseController.class);

    @Autowired
    private LicenseService licenseService;

    @GetMapping(value = "/")
    public ResponseEntity<List<License>> getLicenses(@PathVariable("organizationId") String organizationId) throws TimeoutException {
        logger.info("LicenseServiceController Correlation Id: {}", UserContextHolder.getContext().getCorrelationId());
        List<License> licenses = new ArrayList<>();
        //return ResponseEntity.ok(licenseService.getLicensesByOrganization(organizationId));
        return ResponseEntity.ok(licenses);
    }

    @GetMapping(value = "/{licenseId}")
    public ResponseEntity<License> getLicense(@PathVariable("organizationId") String organizationId, @PathVariable("licenseId") String licenseId) {
        License license = this.licenseService.getLicense(licenseId, organizationId);

        license.add(linkTo(methodOn(LicenseController.class).getLicense(organizationId, license.getLicenseId())).withSelfRel(),
                linkTo(methodOn(LicenseController.class).createLicense(license)).withRel("createLicense"),
                linkTo(methodOn(LicenseController.class).updateLicense(license)).withRel("updateLicense"),
                linkTo(methodOn(LicenseController.class).deleteLicense(license.getLicenseId())).withRel("deleteLicense"));

        return ResponseEntity.ok(license);
    }

    @PutMapping
    public ResponseEntity<License> updateLicense(@RequestBody License license) {
        return ResponseEntity.ok(this.licenseService.updateLicense(license));
    }

    @PostMapping
    public ResponseEntity<License> createLicense(@RequestBody License license) {
        return new ResponseEntity<>(this.licenseService.createLicense(license), HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{licenseId}")
    public ResponseEntity<String> deleteLicense(@PathVariable("licenseId") String licenseId) {
        String response = this.licenseService.deleteLicense(licenseId);
        return ResponseEntity.ok(response);
    }

    @RequestMapping(value = "/{licenseId}/{clientType}", method = RequestMethod.GET)
    public ResponseEntity<License> getLicenseWithClient(
            @PathVariable("organizationId") String organizationId,
            @PathVariable("licenseId") String licenseId,
            @PathVariable("clientType") String clientType) {

        License license = licenseService.getLicense(organizationId, licenseId, clientType);
        return new ResponseEntity<>(license, HttpStatus.OK);

    }

    @GetMapping("/test/{id}")
    public ResponseEntity<String> methodTest(@PathVariable String id) {
        String value = "Se obtuvo el id: " + id;
        return new ResponseEntity<>(value, HttpStatus.OK);
    }
}
