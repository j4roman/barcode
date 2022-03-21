package com.example.j4roman.barcode.service.impl;

import com.example.j4roman.barcode.persistance.dao.BCAlgorithmDAO;
import com.example.j4roman.barcode.persistance.dao.ClientDAO;
import com.example.j4roman.barcode.service.exceptions.DAOException;
import com.example.j4roman.barcode.service.exceptions.EntityAlreadyExistsException;
import com.example.j4roman.barcode.service.exceptions.EntityDoesNotExistException;
import com.example.j4roman.barcode.persistance.dao.Client2AlgorithmDAO;
import com.example.j4roman.barcode.persistance.entities.BCAlgorithm;
import com.example.j4roman.barcode.persistance.entities.Client;
import com.example.j4roman.barcode.persistance.entities.Client2algorithm;
import com.example.j4roman.barcode.service.ClientService;
import com.example.j4roman.barcode.service.dto.manage.Client2algorithmDTO;
import com.example.j4roman.barcode.service.dto.manage.ClientDTO;
import com.example.j4roman.barcode.service.exceptions.UnexpectedDAOException;
import com.example.j4roman.barcode.service.utils.EntityDTOConverter;
import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ClientServiceImpl implements ClientService {

    private static final Logger log = LoggerFactory.getLogger(ClientServiceImpl.class);
    private static final int DB_CALL_TIMEOUT = 30;

    @Autowired
    private ClientDAO clientDAO;

    @Autowired
    private Client2AlgorithmDAO client2AlgorithmDAO;

    @Autowired
    private BCAlgorithmDAO bcAlgorithmDAO;

    @Override
    @Transactional(timeout = DB_CALL_TIMEOUT)
    public ClientDTO create(ClientDTO clientReq) {
        log.debug("Client '{}' is creating from {}", clientReq.getCode(), clientReq);
        try {
            String upperCode = clientReq.getCode().toUpperCase();
            Client foundClient = clientDAO.getByCode(upperCode);
            if (foundClient == null) {
                // map with [algorithmName - algorithm] to bind client2algorithm with algorithms
                Map<String, BCAlgorithm> nameAlgorithmMap = new HashMap<>();
                if (clientReq.getAlgorithms() != null && !clientReq.getAlgorithms().isEmpty()) {
                    // getting all algorithms from BCAlgorithm table
                    List<BCAlgorithm> algorithms = bcAlgorithmDAO.getByNames(
                            clientReq.getAlgorithms()
                                    .stream()
                                    .map(clt -> clt.getAlgorithmName().toUpperCase())
                                    .collect(Collectors.toList())
                    );
                    log.debug("For the client '{}' the next algorithms have been found: {}", clientReq.getCode(), algorithms);
                    // populate map with actual algorithms
                    for (Client2algorithmDTO c2aDTO : clientReq.getAlgorithms()) {
                        Optional<BCAlgorithm> algorithmOpt = algorithms
                                .stream()
                                .filter(alg -> alg.getName().equals(c2aDTO.getAlgorithmName().toUpperCase()))
                                .findFirst();
                        if (algorithmOpt.isPresent()) {
                            nameAlgorithmMap.put(c2aDTO.getAlgorithmName().toUpperCase(), algorithmOpt.get());
                        } else {
                            log.debug("For the client '{}' there is no algorithm with name {}", clientReq.getCode(), c2aDTO.getAlgorithmName().toUpperCase());
                            throw new EntityDoesNotExistException(c2aDTO.getAlgorithmName().toUpperCase());
                        }
                    }
                }
                Client newClient = EntityDTOConverter.convert(clientReq, nameAlgorithmMap);
                clientDAO.create(newClient);
                log.debug("Client '{}' is created", newClient.getCode());
                return EntityDTOConverter.convert(newClient);
            } else {
                log.error("Client '{}' already exists", clientReq.getCode());
                throw new EntityAlreadyExistsException(upperCode);
            }
        } catch (HibernateException e) {
            log.error("DB processing error while creating client '{}'", clientReq.getCode());
            throw new UnexpectedDAOException(e);
        }
    }


    @Override
    @Transactional(timeout = DB_CALL_TIMEOUT)
    public ClientDTO update(ClientDTO clientReq) {
        log.debug("Client '{}' is updating from {}", clientReq.getCode(), clientReq);
        try {
            String upperCode = clientReq.getCode().toUpperCase();
            final Client foundClient = clientDAO.getByCode(upperCode);
            if (foundClient != null) {
                log.debug("Found client '{}'", foundClient.toString());
                boolean isUpd = false;
                if (clientReq.getName() != null) {
                    foundClient.setName(clientReq.getName());
                    isUpd = true;
                }
                if (clientReq.getDescription() != null) {
                    foundClient.setDescription(clientReq.getDescription());
                    isUpd = true;
                }
                if (clientReq.getAlgorithms() != null) {
                    client2AlgorithmDAO.deleteByClient(foundClient);
                    if (!clientReq.getAlgorithms().isEmpty()) {
                        // getting all algorithms from BCAlgorithm table
                        List<BCAlgorithm> algorithms = bcAlgorithmDAO.getByNames(
                                clientReq.getAlgorithms()
                                        .stream()
                                        .map(clt -> clt.getAlgorithmName().toUpperCase())
                                        .collect(Collectors.toList())
                        );
                        log.debug("For the client '{}' the next algorithms have been found: {}", clientReq.getCode(), algorithms);
                        // populate map with actual algorithms
                        Set<Client2algorithm> newClient2Algs = new HashSet<>();
                        for (Client2algorithmDTO c2aDTO : clientReq.getAlgorithms()) {
                            Optional<BCAlgorithm> algorithmOpt = algorithms
                                    .stream()
                                    .filter(alg -> alg.getName().equals(c2aDTO.getAlgorithmName().toUpperCase()))
                                    .findFirst();
                            if (algorithmOpt.isPresent()) {
                                newClient2Algs.add(EntityDTOConverter.convert(c2aDTO, foundClient, algorithmOpt.get()));
                            } else {
                                throw new EntityDoesNotExistException(c2aDTO.getAlgorithmName().toUpperCase());
                            }
                        }
                        foundClient.setClient2algorithms(newClient2Algs);
                    } else {
                        foundClient.getClient2algorithms().clear();
                    }
                    isUpd = true;
                }
                if (isUpd) {
                    clientDAO.change(foundClient);
                    log.debug("Client '{}' is updated", clientReq.getCode());
                } else {
                    log.debug("No need to update client '{}'", clientReq.getCode());
                }
                return EntityDTOConverter.convert(foundClient);
            } else {
                log.error("Client '{}' does not exist", clientReq.getCode());
                throw new EntityDoesNotExistException(upperCode);
            }
        } catch (HibernateException e) {
            log.error("DB processing error while updating client '{}'", clientReq.getCode());
            throw new UnexpectedDAOException(e);
        }
    }

    @Override
    @Transactional(timeout = DB_CALL_TIMEOUT)
    public void deleteByCode(String code) {
        log.debug("Client '{}' is deleting", code);
        try {
            String upperCode = code.toUpperCase();
            Client foundClient = clientDAO.getByCode(upperCode);
            if (foundClient != null) {
                log.debug("Found client '{}'", foundClient.toString());
                clientDAO.delete(foundClient);
                log.debug("Client '{}' is deleted", code);
            } else {
                log.error("Client '{}' does not exist", code);
                throw new EntityDoesNotExistException(upperCode);
            }
        } catch (HibernateException e) {
            log.error("DB processing error while deleting client '{}'", code);
            throw new UnexpectedDAOException(e);
        }
    }

    @Override
    @Transactional(timeout = DB_CALL_TIMEOUT)
    public ClientDTO getByCode(String code) {
        log.debug("Client '{}' is getting", code);
        try {
            String upperCode = code.toUpperCase();
            Client foundClient = clientDAO.getByCode(upperCode);
            if (foundClient != null) {
                log.debug("Found client '{}'", foundClient.toString());
                return EntityDTOConverter.convert(foundClient);
            } else {
                log.error("Client '{}' does not exist", code);
                throw new EntityDoesNotExistException(upperCode);
            }
        } catch (HibernateException e) {
            log.error("DB processing error while getting client '{}'", code);
            throw new UnexpectedDAOException(e);
        }
    }

    @Override
    @Transactional(timeout = DB_CALL_TIMEOUT)
    public List<ClientDTO> getAll() {
        log.debug("Get all the clients");
        try {
            List<Client> allList = clientDAO.getAll();
            return new ArrayList<>(
                    allList.stream()
                            .map(EntityDTOConverter::convert)
                            .collect(Collectors.toList())
            );
        } catch (HibernateException e) {
            log.error("DB processing error while getting the clients");
            throw new UnexpectedDAOException(e);
        }
    }

}
