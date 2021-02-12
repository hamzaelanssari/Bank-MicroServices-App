package org.sid.clientservice.Controller;


import org.sid.clientservice.Model.Client;
import org.sid.clientservice.Repository.ClientRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
public class ClientController {
    final ClientRepository clientRepository;

    public ClientController(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @GetMapping("/clients")
    public List<Client> get(){
        return clientRepository.findAll();
    }

    @GetMapping("/clients/{id}")
    public Client get(@PathVariable Long id){
        return clientRepository.findById(id).get();
    }

    @GetMapping("/clients/byEmail/{email}")
    public Client get(@PathVariable String email){
        return clientRepository.findByEmail(email).get(0);
    }


    @DeleteMapping("/clients/{id}")
    public void delete(@PathVariable Long id){
        clientRepository.deleteById(id);
    }

}
