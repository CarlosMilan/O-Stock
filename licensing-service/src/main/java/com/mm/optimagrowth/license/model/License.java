package com.mm.optimagrowth.license.model;

import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;

/*
Se RepresentationModel para hacer uso de Spring HATEOAS, que nos permite devolver el link al recurso License en el
controlador. Esto nos permite también devolver todos los links que hacen uso de este recurso, por ejemplo los del
CRUD y devolverlos como respuesta para que el cliente sepa cuales son los endpoints respectivos
 */

@Entity
@Table(name = "licenses")
public class License extends RepresentationModel<License> {

    @Id
    @Column(name = "license_id", nullable = false)
    private String licenseId;
    private String description;

    @Column(name = "organization_id", nullable = false)
    private String organizationId;
    @Column(name = "product_name", nullable = false)
    private String productName;
    @Column(name = "license_type", nullable = false)
    private String licenseType;

    private String comment;

    public License withComment(String comment) {
        this.setComment(comment);
        return this;
    }

    public String getLicenseId() {
        return licenseId;
    }

    public void setLicenseId(String licenseId) {
        this.licenseId = licenseId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getLicenseType() {
        return licenseType;
    }

    public void setLicenseType(String licenseType) {
        this.licenseType = licenseType;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "License{" +
                ", licenseId='" + licenseId + '\'' +
                ", description='" + description + '\'' +
                ", organizationId='" + organizationId + '\'' +
                ", productName='" + productName + '\'' +
                ", licenseType='" + licenseType + '\'' +
                '}';
    }
}
