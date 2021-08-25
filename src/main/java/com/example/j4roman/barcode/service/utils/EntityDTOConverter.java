package com.example.j4roman.barcode.service.utils;

import com.example.j4roman.barcode.persistance.entities.Client;
import com.example.j4roman.barcode.persistance.entities.Client2algorithm;
import com.example.j4roman.barcode.service.dto.ActionDTO;
import com.example.j4roman.barcode.service.dto.AlgorithmDTO;
import com.example.j4roman.barcode.persistance.entities.Action;
import com.example.j4roman.barcode.persistance.entities.BCAlgorithm;
import com.example.j4roman.barcode.service.dto.Client2algorithmDTO;
import com.example.j4roman.barcode.service.dto.ClientDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class EntityDTOConverter {

    public static BCAlgorithm convert(AlgorithmDTO algorithmReq) {
        final BCAlgorithm bcAlgorithm = new BCAlgorithm();
        bcAlgorithm.setName(algorithmReq.getName());
        bcAlgorithm.setPattern(algorithmReq.getPattern());
        bcAlgorithm.setDescription(algorithmReq.getDescription());
        if (algorithmReq.getActions() != null && !algorithmReq.getActions().isEmpty()) {
            bcAlgorithm.getActions().addAll(
                    algorithmReq.getActions()
                            .stream()
                            .map(action -> convert(action, bcAlgorithm))
                            .collect(Collectors.toSet())
            );
        }
        return bcAlgorithm;
    }

    public static Action convert(ActionDTO actionReq, BCAlgorithm linkedAlgorithm) {
        Action action = new Action();
        action.setTask(actionReq.getTask());
        action.setOrderNum(actionReq.getOrderNum());
        action.setInd1(actionReq.getInd1());
        action.setInd2(actionReq.getInd2());
        action.setCount(actionReq.getCount());
        action.setDescription(actionReq.getDescription());
        action.setBcAlgorithm(linkedAlgorithm);
        return action;
    }

    public static AlgorithmDTO convert(BCAlgorithm algorithm) {
        AlgorithmDTO algorithmReq = new AlgorithmDTO();
        algorithmReq.setName(algorithm.getName());
        algorithmReq.setPattern(algorithm.getPattern());
        algorithmReq.setDescription(algorithm.getDescription());
        algorithmReq.setActions(new ArrayList<>(
                algorithm.getActions()
                        .stream()
                        .map(EntityDTOConverter::convert)
                        .collect(Collectors.toList())
        ));
        return algorithmReq;
    }

    public static ActionDTO convert(Action action) {
        ActionDTO actionReq = new ActionDTO();
        actionReq.setTask(action.getTask());
        actionReq.setOrderNum(action.getOrderNum());
        actionReq.setInd1(action.getInd1());
        actionReq.setInd2(action.getInd2());
        actionReq.setCount(action.getCount());
        actionReq.setDescription(action.getDescription());
        return actionReq;
    }

    public static Client convert(ClientDTO clientDTO, Map<String, BCAlgorithm> algorithmNameMap) {
        final Client client = new Client();
        client.setCode(clientDTO.getCode());
        client.setName(clientDTO.getName());
        client.setDescription(clientDTO.getDescription());
        if (clientDTO.getAlgorithms() != null && !clientDTO.getAlgorithms().isEmpty() && !algorithmNameMap.isEmpty()) {
            client.getClient2algorithms().addAll(
                    clientDTO.getAlgorithms()
                    .stream()
                    .map(c2a -> convert(c2a, client, algorithmNameMap.get(c2a.getAlgorithmName())
                    ))
                    .collect(Collectors.toSet())
            );
        }
        return client;
    }

    public static Client2algorithm convert(Client2algorithmDTO client2algReq, Client client, BCAlgorithm algorithm) {
        Client2algorithm client2alg = new Client2algorithm();
        client2alg.setSpecValue(client2algReq.getSpecValue());
        client2alg.setClient(client);
        client2alg.setBcAlgorithm(algorithm);
        return client2alg;
    }

    public static ClientDTO convert(Client client) {
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setCode(client.getCode());
        clientDTO.setName(client.getName());
        clientDTO.setDescription(client.getDescription());
        clientDTO.setAlgorithms(new ArrayList<>(
                client.getClient2algorithms()
                        .stream()
                        .map(EntityDTOConverter::convert)
                        .collect(Collectors.toList())
        ));
        return clientDTO;
    }

    public static Client2algorithmDTO convert(Client2algorithm client2alg) {
        Client2algorithmDTO client2algDTO = new Client2algorithmDTO();
        client2algDTO.setSpecValue(client2alg.getSpecValue());
        client2algDTO.setAlgorithmName(client2alg.getBcAlgorithm().getName());
        return client2algDTO;
    }
}
