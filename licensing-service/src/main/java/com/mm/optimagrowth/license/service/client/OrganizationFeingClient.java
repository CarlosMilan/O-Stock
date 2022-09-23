package com.mm.optimagrowth.license.service.client;

import com.mm.optimagrowth.license.model.Organization;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/*
La tercera alternativa para implementar un cliente de un servicio es usar Netflix Feing, que hace uso de una interfaz y
de anotaciones para mapear que servicio registrado en Eureka se va a invocar haciendo uso del Balanceador de carga.
Spring Cloud Framework generará una clase Proxy para invocar al servicio objetivo.
El primer paso es agregar la anotación @EnableFeingClients a la clase bootstrap de la aplicación, pero para ello,
hay que agregar la dependencia de spring-cloud-starter-openfeign
 */
@FeignClient("organization-service")
public interface OrganizationFeingClient {

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/v1/organization/{organizationId}",
            consumes = "application/json")
    Organization getOrganization(@PathVariable("organizationId") String organizationId);

    /*
    Por detrás Feign interpretará los códigos de respuesta HTTP y en caso de recibir un código 4xx o 5xx, estos
    se reflejarán en una excepción FeignException, esta excepción se puede personalizar:
    https://github.com/OpenFeign/feign/wiki/Custom-error-handling
     */
}
