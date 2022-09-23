package com.mm.optimagrowth.license.service.client;

import com.mm.optimagrowth.license.model.Organization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/*
Esta es la primera opción para definir un cliente de un servicio. El problema con esta alternativa es que
obtenemos todas las instancias del servicio que queremos llamar mediante DiscoveryClient, pero el trabajo
de seleccionar la instancia que queremos llamar corre por nuestra cuenta, es decir, nosotros tenemos que
implementar el balanceador de carga manualmente aquí. Un segundo inconveniente es que tenemos que definir
manualmente la url o el endpoint que queremos consumir.
 */
@Component
public class OrganizationDiscoveryClient {

    @Autowired
    private DiscoveryClient discoveryClient;

    public Organization getOrganization(String organizationId) {
        RestTemplate restTemplate = new RestTemplate();
        List<ServiceInstance> instances = discoveryClient.getInstances("organization-service");

        if (instances.size() == 0)
            return null;
        String serviceUri = String.format("%s/v1/organization/%s", instances.get(0).getUri().toString(),organizationId);

        ResponseEntity<Organization> restExchange = restTemplate.exchange(serviceUri,
                HttpMethod.GET, null, Organization.class, organizationId);

        return restExchange.getBody();
    }
}
