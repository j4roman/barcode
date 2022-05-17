package com.example.j4roman.barcode.service.utils.tasks;

import com.example.j4roman.barcode.persistance.entities.Action;
import com.example.j4roman.barcode.persistance.entities.Client2algorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controls the process of collecting and using {@link TaskFactory}s, creating {@link Task}s etc.
 *
 */

@Component
public class Tasks {

    private static final Logger logger = LoggerFactory.getLogger(Tasks.class);

    private final Map<String, TaskFactory> factoryMap;

    @Autowired
    public Tasks(List<TaskFactory> taskFactoryList) {
        factoryMap = new HashMap<>();
        logger.debug("Registered tasks: {}", taskFactoryList);
        for (TaskFactory taskFactory : taskFactoryList) {
            factoryMap.put(taskFactory.getTaskType(), taskFactory);
        }
    }

    /**
     * Generates {@link Task} for specific {@link Action} and {@link Client2algorithm}
     * @param action contains instance specific parameters for generating {@link Task}
     * @param c2a contains client specific parameters for generating {@link Task}
     * @return
     */
    public Task getTask(Action action, Client2algorithm c2a) {
        return factoryMap.get(action.getTask()).createTask(action, c2a);
    }

    /**
     * Generates default description for {@link Action}
     * @param action contains instance specific parameters for description
     * @return
     */
    public String getDescription(Action action) {
        return factoryMap.get(action.getTask()).createDefaultDescription(action);
    }

    /**
     * Checks existence of the specific {@link Action} task
     * @param action checking {@link Action}
     * @return true if {@link Action}'s task is registered in the application, false - otherwise
     */
    public boolean checkTaskType(Action action) {
        return factoryMap.keySet().contains(action.getTask());
    }
}
