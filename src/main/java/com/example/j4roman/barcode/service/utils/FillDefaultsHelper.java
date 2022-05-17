package com.example.j4roman.barcode.service.utils;

import com.example.j4roman.barcode.persistance.entities.Action;
import com.example.j4roman.barcode.persistance.entities.BCAlgorithm;
import com.example.j4roman.barcode.service.utils.tasks.Tasks;

/**
 * Class-helper for filling the empty fields with default values
 *
 */
public final class FillDefaultsHelper {

    public static void fillTasks(Tasks tasks, BCAlgorithm algorithm) {
        for (Action action : algorithm.getActions()) {
            if (action.getDescription() == null) {
                action.setDescription(tasks.getDescription(action));
            }
        }
    }
}
