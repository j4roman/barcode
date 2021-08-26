package com.example.j4roman.barcode.service.utils;

import com.example.j4roman.barcode.persistance.entities.Action;
import com.example.j4roman.barcode.persistance.entities.BCAlgorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class ToBarcodeFactory {

    public static ToBarcode create(BCAlgorithm algorithm, String specValue) {
        // Create ordered list of tasks
        final List<CommonTasks.Task> taskList = new ArrayList<>(algorithm.getActions()
                .stream()
                .sorted((act1, act2) -> (int)(act1.getOrderNum() - act2.getOrderNum()))
                .map(action -> CommonTasks.generate(action, specValue))
                .collect(Collectors.toList())
        );

        return (val) -> {
            StringBuilder result = new StringBuilder(val);
            taskList.forEach(task -> task.exec(result));
            return result.toString();
        };
    }
}
