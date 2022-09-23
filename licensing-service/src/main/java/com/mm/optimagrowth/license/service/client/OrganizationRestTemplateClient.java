package com.mm.optimagrowth.license.service.client;

import com.mm.optimagrowth.license.model.Organization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/*
Esta es la segunda opción, donde hemos definido un bean (en la clase Bootstrap) de RestTemplate al cuál le
hemos agregado un Balanceador de carga usando @LoadBalanced, esto nos va a permitir construir la url usando
directamente el ID del servicio en el servicio Eureka
 */

@Component
public class OrganizationRestTemplateClient {

    @Autowired
    RestTemplate restTemplate;

    public Organization getOrganization(String organizationId) {
        ResponseEntity<Organization> restExchange = restTemplate.exchange(
                "http://organization-service/v1/organization/{organizationId}",
                HttpMethod.GET, null,
                Organization.class,
                organizationId);
        return restExchange.getBody();
    }

    /*
    En este caso, la url utilizada es ".../organization-service/...", este método permite abstraer completamente la
    dirección física del servicio y su puerto. Este trabajo lo hará el balanceador de carga seleccionando la instancia
    mediante un algoritmo round-robin
     */
}
