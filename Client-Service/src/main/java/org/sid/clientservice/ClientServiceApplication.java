package org.sid.clientservice;


import org.sid.clientservice.Model.Client;
import org.sid.clientservice.Repository.ClientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.core.config.Projection;


@SpringBootApplication
public class ClientServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClientServiceApplication.class, args);
    }
    @Bean
    CommandLineRunner start(ClientRepository clientRepository){
        return args -> {
            clientRepository.save(new Client(null,"Hamza","hamza@gmail.com"));
            clientRepository.save(new Client(null,"Ahmed","ahmed@gmail.com"));
            clientRepository.save(new Client(null,"Monir","monir@gmail.com"));
            clientRepository.save(new Client(null,"Salah","salah@gmail.com"));

            clientRepository.findAll().forEach(System.out::println);
        };
    }

}


@Projection(name = "FullClient",types = Client.class)
interface FullCustomerProjection extends Projection{
    Long getId();
    String getName();
    String getEmail();
}

@Projection(name = "NameClient",types = Client.class)
interface NameCustomerProjection extends Projection{
    String getName();
}