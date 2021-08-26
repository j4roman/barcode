package com.example.j4roman.barcode.service.utils;

import com.example.j4roman.barcode.persistance.entities.BCAlgorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * The fabric creates functions to generate and parse barcodes
 *
 */
public final class BarcodeFuncFactory {

    /**
     * The method creates the function which task is to generate barcode from value
     *
     * @param algorithm algorithm to generate
     * @param specValue special client String value used in generation process
     * @return function used to generate barcodes from value
     */
    public static Function<String, String> toBarcode(BCAlgorithm algorithm, String specValue) {
        // Create list of tasks ordered by orderNum
        final List<CommonTasks.Task> taskList = new ArrayList<>(algorithm.getActions()
                .stream()
                .sorted((act1, act2) -> (int)(act1.getOrderNum() - act2.getOrderNum()))
                .map(action -> CommonTasks.generate(action, specValue))
                .collect(Collectors.toList())
        );

        // Generate function
        return val -> {
            StringBuilder result = new StringBuilder(val);
            taskList.forEach(task -> task.exec(result));
            return result.toString();
        };
    }

    /**
     * The method creates the function which task is to parse barcode into value.
     * It uses generation algorithm in reverse order.
     * Also tests value validity.
     *
     * @param algorithm algorithm used to generate
     * @param specValue special client String value used in generation process
     * @return function used to parse barcodes into value
     */
    public static Function<String, String> fromBarcode(BCAlgorithm algorithm, String specValue) {
        // Create list of tasks reverse ordered by orderNum
        final List<CommonTasks.Task> taskList = new ArrayList<>(algorithm.getActions()
                .stream()
                .sorted((act1, act2) -> (int)(act2.getOrderNum() - act1.getOrderNum()))
                .map(action -> CommonTasks.generate(action, specValue, true))
                .collect(Collectors.toList())
        );

        // Parse function
        return val -> {
            StringBuilder result = new StringBuilder(val);
            taskList.forEach(task -> task.exec(result));
            return result.toString();
        };
    }
}
