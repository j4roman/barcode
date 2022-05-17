package com.example.j4roman.barcode.service.utils.tasks.impl;

import com.example.j4roman.barcode.persistance.entities.Action;
import com.example.j4roman.barcode.persistance.entities.Client2algorithm;
import com.example.j4roman.barcode.service.utils.tasks.Task;
import com.example.j4roman.barcode.service.utils.tasks.TaskFactory;
import org.springframework.stereotype.Component;

/**
 * Generates specific {@link AddClientValueTask} with TYPE = <code>""ADD_CLT"</code><br/>
 * The task performs inserting of character from ind2 position of client's <code>specValue</code> into ind1 position
 *
 */
@Component
public class AddClientValueTaskFactory extends TaskFactory {

    private static final String TASK_TYPE = "ADD_CLT";
    private static final String DESCRIPTION_PATTERN = "Insert char from ind2 (%s) position of client's specValue into ind1 (%s) position";

    public AddClientValueTaskFactory() {
        super(TASK_TYPE);
    }

    @Override
    public Task createTask(Action action, Client2algorithm c2a) {
        return new AddClientValueTask((int) action.getInd1().longValue(), (int) action.getInd2().longValue(), c2a.getSpecValue());
    }

    @Override
    public String createDefaultDescription(Action action) {
        return String.format(DESCRIPTION_PATTERN, action.getInd2(), action.getInd1());
    }
}
