package com.example.j4roman.barcode.service.utils.tasks.impl;

import com.example.j4roman.barcode.persistance.entities.Action;
import com.example.j4roman.barcode.persistance.entities.Client2algorithm;
import com.example.j4roman.barcode.service.utils.tasks.Task;
import com.example.j4roman.barcode.service.utils.tasks.TaskFactory;
import org.springframework.stereotype.Component;

/**
 * Generates specific {@link AddClientValueTask} with TYPE = <code>"SWP1"</code><br/>
 * The task performs swapping of characters at ind1 and ind2 positions
 *
 */
@Component
public class SwapSimpleTaskFactory extends TaskFactory {

    private static final String TASK_TYPE = "SWP1";
    private static final String DESCRIPTION_PATTERN = "Swap ind1 (%d) and ind2 (%d) chars";

    public SwapSimpleTaskFactory() {
        super(TASK_TYPE);
    }

    @Override
    public Task createTask(Action action, Client2algorithm c2a) {
        return new SwapSimpleTask((int) action.getInd1().longValue(), (int) action.getInd2().longValue());
    }

    @Override
    public String createDefaultDescription(Action action) {
        return String.format(DESCRIPTION_PATTERN, action.getInd1(), action.getInd2());
    }
}
