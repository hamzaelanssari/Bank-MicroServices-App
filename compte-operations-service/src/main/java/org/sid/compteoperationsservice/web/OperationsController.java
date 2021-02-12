package org.sid.compteoperationsservice.web;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.sid.compteoperationsservice.entities.Compte;
import org.sid.compteoperationsservice.entities.Operation;
import org.sid.compteoperationsservice.entities.repos.CompteRepository;
import org.sid.compteoperationsservice.entities.repos.OperationRepository;
import org.sid.compteoperationsservice.feign.ClientRestClient;
import org.sid.compteoperationsservice.model.CompteEtats;
import org.sid.compteoperationsservice.model.OperationTypes;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Column;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// OLD
// OLD
// OLD
// OLD
// OLD
// OLD
// OLD
// TO DELETE
// TO DELETE
// TO DELETE
// TO DELETE
// TO DELETE
// TO DELETE
@RestController
@CrossOrigin("*")
public class OperationsController {
    @Autowired
    private CompteRepository compteRepository;
    @Autowired
    private OperationRepository operationRepository;
    @Autowired
    private ClientRestClient clientRestClient;

    @Transactional
    @PostMapping("/versement")
    public Operation versementRetrait(@RequestBody CompteVersement compteVersement) {
        Operation operation = new Operation();
        operation.setMontant(compteVersement.getMontant());
        operation.setType(compteVersement.getType());
        operationRepository.save(operation);
        Compte compte = compteRepository.getOne(compteVersement.getCompteId());
        operation.setCompte(compte);
        if (compteVersement.getType().equals(OperationTypes.CREDIT))
            compteVersement.setMontant(compteVersement.getMontant() * -1);
        compte.setSolde(compteVersement.getMontant() + compte.getSolde());
        if (compteVersement.getDate() != null)
            operation.setDate(compteVersement.getDate());
        else operation.setDate(LocalDateTime.now());
        compteRepository.save(compte);
        return operation;
    }

    @Transactional
    @PostMapping("/virement")
    public void virement(@RequestBody Virement virement) {
        Compte compteSource = compteRepository.getOne(virement.getCompteIdSource());
        Compte compteDest = compteRepository.getOne(virement.getCompteIdDest());
        Operation sourceOperation = new Operation(null, virement.getDate(), virement.getMontant(), OperationTypes.CREDIT, compteSource);
        Operation destOperation = new Operation(null, virement.getDate(), virement.getMontant(), OperationTypes.DEBIT, compteDest);
        compteSource.setSolde(compteSource.getSolde() - virement.getMontant());
        compteSource.setSolde(compteSource.getSolde() + virement.getMontant());
        List<Operation> operationList = new ArrayList<>();
        operationList.add(sourceOperation);
        operationList.add(destOperation);
        List<Compte> compteList = new ArrayList<>();
        compteList.add(compteDest);
        compteList.add(compteSource);
        compteRepository.saveAll(compteList);
        operationRepository.saveAll(operationList);
    }
    @Transactional
    @PostMapping("/compte/{id}/activate")
    public void activateCompte(@PathVariable Long id){
            Compte compte = compteRepository.getOne(id);
            compte.setEtat(CompteEtats.ACTIVE);
            compteRepository.save(compte);
    }

    @Transactional
    @PostMapping("/compte/{id}/suspend")
    public void suspendCompte(@PathVariable Long id){
            Compte compte = compteRepository.getOne(id);
            compte.setEtat(CompteEtats.SUSPENDED);
            compteRepository.save(compte);
    }

}

@Data
@AllArgsConstructor
@NoArgsConstructor
class CompteVersement {
    private Double montant;
    private Long compteId;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    @Column(nullable = true)
    private LocalDateTime date;
    private String type;

}

@Data
@AllArgsConstructor
@NoArgsConstructor
class Virement {
    private Double montant;
    private Long compteIdSource;
    private Long compteIdDest;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    @Column(nullable = true)
    private LocalDateTime date;
}
