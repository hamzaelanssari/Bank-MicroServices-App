package org.sid.compteoperationsservice.service;

import org.sid.compteoperationsservice.entities.Compte;
import org.sid.compteoperationsservice.entities.Operation;
import org.sid.compteoperationsservice.model.CompteOperation;
import org.springframework.data.domain.Page;

public interface OperationsService {
    public Compte addCompte(Long clientId, String typeCompte);

    public Operation versment(CompteOperation compteOperation);

    public Operation retrait(CompteOperation compteOperation);

    public Page<Operation> getOperationsPaginated(Long compteId, int page, int size);

    public Compte getCompteByClient(Long clientId);

    public void activateCompte(Long compteId);

    public void suspendCompte(Long compteId);
}
