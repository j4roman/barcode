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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ClientServiceImpl implements ClientService {

    private static final Logger logger = LogManager.getLogger(ClientServiceImpl.class);
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
                    logger.debug("Found algorithms {}", algorithms);
                    // populate map with actual algorithms
                    for (Client2algorithmDTO c2aDTO : clientReq.getAlgorithms()) {
                        Optional<BCAlgorithm> algorithmOpt = algorithms
                                .stream()
                                .filter(alg -> alg.getName().equals(c2aDTO.getAlgorithmName().toUpperCase()))
                                .findFirst();
                        if (algorithmOpt.isPresent()) {
                            nameAlgorithmMap.put(c2aDTO.getAlgorithmName().toUpperCase(), algorithmOpt.get());
                        } else {
                            throw new EntityDoesNotExistException(c2aDTO.getAlgorithmName().toUpperCase());
                        }
                    }
                }
                Client newClient = EntityDTOConverter.convert(clientReq, nameAlgorithmMap);
                clientDAO.create(newClient);
                return EntityDTOConverter.convert(newClient);
            } else {
                throw new EntityAlreadyExistsException(upperCode);
            }
        } catch (HibernateException e) {
            throw new UnexpectedDAOException(e);
        }
    }


    @Override
    @Transactional(timeout = DB_CALL_TIMEOUT)
    public ClientDTO update(ClientDTO clientReq) {
        try {
            String upperCode = clientReq.getCode().toUpperCase();
            final Client foundClient = clientDAO.getByCode(upperCode);
            if (foundClient != null) {
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
                        logger.debug("Found algorithms {}", algorithms);
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
                }
                return EntityDTOConverter.convert(foundClient);
            } else {
                throw new EntityDoesNotExistException(upperCode);
            }
        } catch (HibernateException e) {
            throw new UnexpectedDAOException(e);
        }
    }

    @Override
    @Transactional(timeout = DB_CALL_TIMEOUT)
    public void deleteByCode(String code) {
        try {
            String upperCode = code.toUpperCase();
            Client foundClient = clientDAO.getByCode(upperCode);
            if (foundClient != null) {
                clientDAO.delete(foundClient);
            } else {
                throw new EntityDoesNotExistException(upperCode);
            }
        } catch (HibernateException e) {
            throw new UnexpectedDAOException(e);
        }
    }

    @Override
    @Transactional(timeout = DB_CALL_TIMEOUT)
    public ClientDTO getByCode(String code) {
        try {
            String upperCode = code.toUpperCase();
            Client foundClient = clientDAO.getByCode(upperCode);
            if (foundClient != null) {
                return EntityDTOConverter.convert(foundClient);
            } else {
                throw new EntityDoesNotExistException(upperCode);
            }
        } catch (HibernateException e) {
            throw new UnexpectedDAOException(e);
        }
    }

    @Override
    @Transactional(timeout = DB_CALL_TIMEOUT)
    public List<ClientDTO> getAll() {
        try {
            List<Client> allList = clientDAO.getAll();
            return new ArrayList<>(
                    allList.stream()
                            .map(EntityDTOConverter::convert)
                            .collect(Collectors.toList())
            );
        } catch (HibernateException e) {
            throw new UnexpectedDAOException(e);
        }
    }

}
