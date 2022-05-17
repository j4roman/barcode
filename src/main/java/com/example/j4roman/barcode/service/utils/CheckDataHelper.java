package com.example.j4roman.barcode.service.utils;

import com.example.j4roman.barcode.persistance.entities.Action;
import com.example.j4roman.barcode.persistance.entities.BCAlgorithm;
import com.example.j4roman.barcode.service.utils.tasks.exceptions.NoSuchTaskException;
import com.example.j4roman.barcode.service.utils.tasks.Tasks;

/**
 * Class-helper for performing necessary checks
 *
 */
public final class CheckDataHelper {

    public static boolean checkTasks(Tasks tasks, BCAlgorithm algorithm) {
        for (Action action : algorithm.getActions()) {
            if (!tasks.checkTaskType(action)) {
                throw new NoSuchTaskException(action.getTask());
            }
        }
        return true;
    }
}
