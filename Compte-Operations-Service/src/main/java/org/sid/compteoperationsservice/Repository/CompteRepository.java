package org.sid.compteoperationsservice.Repository;

import org.sid.compteoperationsservice.Model.Compte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface CompteRepository extends JpaRepository<Compte,Long> {
    public Compte getCompteByIdClient(Long ClientId);

}
