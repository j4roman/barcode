package com.example.j4roman.barcode.service.utils.tasks.impl;

import com.example.j4roman.barcode.persistance.entities.Action;
import com.example.j4roman.barcode.persistance.entities.Client2algorithm;
import com.example.j4roman.barcode.service.utils.tasks.Task;
import com.example.j4roman.barcode.service.utils.tasks.TaskFactory;
import org.springframework.stereotype.Component;

/**
 * Generates specific {@link AddClientValueTask} with TYPE = <code>""INS"</code><br/>
 * The task performs inserting of fixed digit with value ind2 into position ind1
 *
 */
@Component
public class InsertDigitTaskFactory extends TaskFactory {

    private static final String TASK_TYPE = "INS";
    private static final String DESCRIPTION_PATTERN = "Insert fixed digit with value ind2 (%s) into position ind1 (%s)";

    public InsertDigitTaskFactory() {
        super(TASK_TYPE);
    }

    @Override
    public Task createTask(Action action, Client2algorithm c2a) {
        return new InsertDigitTask((int) action.getInd1().longValue(), (int) action.getInd2().longValue());
    }

    @Override
    public String createDefaultDescription(Action action) {
        return String.format(DESCRIPTION_PATTERN, action.getInd2(), action.getInd1());
    }
}
