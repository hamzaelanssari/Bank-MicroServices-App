package org.sid.compteoperationsservice.Operations;

import org.sid.compteoperationsservice.Model.Compte;
import org.sid.compteoperationsservice.Model.CompteOperation;
import org.sid.compteoperationsservice.Model.Operation;
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
