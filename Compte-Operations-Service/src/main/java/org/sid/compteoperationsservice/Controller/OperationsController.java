package org.sid.compteoperationsservice.Controller;

import org.sid.compteoperationsservice.Model.*;
import org.sid.compteoperationsservice.Repository.CompteRepository;
import org.sid.compteoperationsservice.Repository.OperationRepository;
import org.sid.compteoperationsservice.feign.ClientRestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
        operation.setPrice(compteVersement.getPrice());
        operation.setType(compteVersement.getType());
        operationRepository.save(operation);
        Compte compte = compteRepository.getOne(compteVersement.getCompteId());
        operation.setCompte(compte);
        if (compteVersement.getType().equals(OperationTypes.CREDIT))
            compteVersement.setPrice(compteVersement.getPrice() * -1);
        compte.setSolde(compteVersement.getPrice() + compte.getSolde());
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
        Operation sourceOperation = new Operation(null, virement.getDate(), virement.getPrice(), OperationTypes.CREDIT, compteSource);
        Operation destOperation = new Operation(null, virement.getDate(), virement.getPrice(), OperationTypes.DEBIT, compteDest);
        compteSource.setSolde(compteSource.getSolde() - virement.getPrice());
        compteSource.setSolde(compteSource.getSolde() + virement.getPrice());
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

