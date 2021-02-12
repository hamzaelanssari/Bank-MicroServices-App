package org.sid.clientservice.Repository;

import org.sid.clientservice.Model.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

@RepositoryRestResource
public interface ClientRepository extends JpaRepository<Client,Long> {
    @RestResource(path = "/byName")
    Page<Client> findByNameContains(@Param("kw") String name, Pageable pageable);

    List<Client> findByEmail(String email);
}