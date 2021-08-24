package com.example.j4roman.barcode.controller.utils;

import com.example.j4roman.barcode.controller.api.ActionRequest;
import com.example.j4roman.barcode.controller.api.AlgorithmRequest;
import com.example.j4roman.barcode.persistance.entities.Action;
import com.example.j4roman.barcode.persistance.entities.BCAlgorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class RequestEntityConverter {

    public static BCAlgorithm convert(AlgorithmRequest algorithmReq) {
        BCAlgorithm bcAlgorithm = new BCAlgorithm();
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
        } else if (algorithmReq.getActions() == null) {
            bcAlgorithm.setActions(null);
        }
        return bcAlgorithm;
    }

    private static Action convert(ActionRequest actionReq, BCAlgorithm linkedAlgorithm) {
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

    public static AlgorithmRequest convert(BCAlgorithm algorithm) {
        AlgorithmRequest algorithmReq = new AlgorithmRequest();
        algorithmReq.setName(algorithm.getName());
        algorithmReq.setPattern(algorithm.getPattern());
        algorithmReq.setDescription(algorithm.getDescription());
        List<ActionRequest> newActions = new ArrayList<>();
        newActions.addAll(
                algorithm.getActions()
                        .stream()
                        .map(action -> convert(action))
                        .collect(Collectors.toList())
        );
        algorithmReq.setActions(newActions);
        return algorithmReq;
    }

    private static ActionRequest convert(Action action) {
        ActionRequest actionReq = new ActionRequest();
        actionReq.setTask(action.getTask());
        actionReq.setOrderNum(action.getOrderNum());
        actionReq.setInd1(action.getInd1());
        actionReq.setInd2(action.getInd2());
        actionReq.setCount(action.getCount());
        actionReq.setDescription(action.getDescription());
        return actionReq;
    }
}
