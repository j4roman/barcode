package com.example.j4roman.barcode.service.utils.tasks.impl;

import com.example.j4roman.barcode.persistance.entities.Action;
import com.example.j4roman.barcode.persistance.entities.Client2algorithm;
import com.example.j4roman.barcode.service.utils.tasks.Task;
import com.example.j4roman.barcode.service.utils.tasks.TaskFactory;
import org.springframework.stereotype.Component;

/**
 * Generates specific {@link AddClientValueTask} with TYPE = <code>""CHKNUM1"</code><br/>
 * The task performs adding check number to the end using (1,3,1,3,1...) multipliers
 *
 */
@Component
public class CheckNum1TaskFactory extends TaskFactory {

    private static final String TASK_TYPE = "CHKNUM1";
    private static final String DESCRIPTION_PATTERN = "Add simple check num to the end using (1,3,1,3,1...) multipliers";

    public CheckNum1TaskFactory() {
        super(TASK_TYPE);
    }

    @Override
    public Task createTask(Action action, Client2algorithm c2a) {
        return new CheckNum1Task();
    }

    @Override
    public String createDefaultDescription(Action action) {
        return DESCRIPTION_PATTERN;
    }
}
