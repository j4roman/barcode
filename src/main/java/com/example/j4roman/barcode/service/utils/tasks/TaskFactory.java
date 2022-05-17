package com.example.j4roman.barcode.service.utils.tasks;

import com.example.j4roman.barcode.persistance.entities.Action;
import com.example.j4roman.barcode.persistance.entities.Client2algorithm;

/**
 * Allows to generate specific {@link Task}s
 *
 */
public abstract class TaskFactory {

    private String taskType;

    protected TaskFactory(String taskType) {
        this.taskType = taskType;
    }

    /**
     * Generates {@link Task} for specific {@link Action} and {@link Client2algorithm}
     * @param action contains instance specific parameters for generating {@link Task}
     * @param c2a contains client specific parameters for generating {@link Task}
     * @return
     */
    public abstract Task createTask(Action action, Client2algorithm c2a);

    /**
     * Generates default description for {@link Action}
     * @param action contains instance specific parameters for description
     * @return
     */
    public abstract String createDefaultDescription(Action action);


    public String getTaskType() {
        return taskType;
    }

    @Override
    public String toString() {
        return taskType;
    }
}
