package org.sid.compteoperationsservice.service;

import org.sid.compteoperationsservice.entities.Compte;
import org.sid.compteoperationsservice.entities.Operation;
import org.sid.compteoperationsservice.entities.repos.CompteRepository;
import org.sid.compteoperationsservice.entities.repos.OperationRepository;
import org.sid.compteoperationsservice.feign.ClientRestClient;
import org.sid.compteoperationsservice.model.CompteEtats;
import org.sid.compteoperationsservice.model.CompteOperation;
import org.sid.compteoperationsservice.model.OperationTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class OperationsServiceImpl implements OperationsService {
    @Autowired
    CompteRepository compteRepository;
    @Autowired
    OperationRepository operationRepository;
    @Autowired
    ClientRestClient clientRestClient;

    @Override
    public Compte addCompte(Long clientId, String typeCompte) {
        Compte compte = new Compte();
        compte.setEtat(CompteEtats.ACTIVE);
        compte.setSolde(0d);
        compte.setType(typeCompte);
        compte.setDateCreation(LocalDateTime.now());
        compte.setIdClient(clientId);
        compteRepository.save(compte);
        return compte;
    }

    @Override
    public Operation versment(CompteOperation versementOperation) {
        Operation operation = new Operation();
        operation.setMontant(versementOperation.getMontant());
        operation.setType(OperationTypes.DEBIT);
        operationRepository.save(operation);
        Compte compte = compteRepository.getOne(versementOperation.getCompteId());
        operation.setCompte(compte);
        compte.setSolde(versementOperation.getMontant() + compte.getSolde());
        if (versementOperation.getDate() != null)
            operation.setDate(versementOperation.getDate());
        else operation.setDate(LocalDateTime.now());
        compteRepository.save(compte);
        return operation;
    }

    @Override
    public Operation retrait(CompteOperation retraitOperation) {
        Operation operation = new Operation();
        operation.setMontant(retraitOperation.getMontant());
        operation.setType(OperationTypes.CREDIT);
        operationRepository.save(operation);
        Compte compte = compteRepository.getOne(retraitOperation.getCompteId());
        operation.setCompte(compte);
        compte.setSolde(compte.getSolde() - retraitOperation.getMontant());
        if (retraitOperation.getDate() != null)
            operation.setDate(retraitOperation.getDate());
        else operation.setDate(LocalDateTime.now());
        compteRepository.save(compte);
        return operation;
    }

    @Override
    public Page<Operation> getOperationsPaginated(Long compteId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return operationRepository.getOperationsByCompteId(compteId, pageable);
    }

    @Override
    public Compte getCompteByClient(Long clientId) {
        Compte compte = compteRepository.getCompteByIdClient(clientId);
        compte.setClient(clientRestClient.getClientById(clientId));
        return compte;
    }

    @Override
    public void activateCompte(Long compteId) {
        Compte compte = compteRepository.getOne(compteId);
        compte.setEtat(CompteEtats.ACTIVE);
        compteRepository.save(compte);
    }

    @Override
    public void suspendCompte(Long compteId) {
        Compte compte = compteRepository.getOne(compteId);
        compte.setEtat(CompteEtats.SUSPENDED);
        compteRepository.save(compte);
    }
}
