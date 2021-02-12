package org.sid.compteoperationsservice.feign;
import org.sid.compteoperationsservice.Model.Client;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("client-service")
public interface ClientRestClient {
    @GetMapping(path = "/clients/{id}")
    public Client getClientById(@PathVariable("id") Long id);

}
