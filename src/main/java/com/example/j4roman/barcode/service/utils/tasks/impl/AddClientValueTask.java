package com.example.j4roman.barcode.service.utils.tasks.impl;

import com.example.j4roman.barcode.service.utils.tasks.Task;
import com.example.j4roman.barcode.service.utils.tasks.exceptions.ClientNumberInvalidException;

/**
 * The task performs inserting character from ind2 position of client's <code>specValue</code> into ind1 position
 *
 */
public class AddClientValueTask implements Task {

    private final int pos1;
    private final char ind2char;

    public AddClientValueTask(int ind1, int ind2, String specValue) {
        this.pos1 = ind1;
        this.ind2char = specValue.charAt(ind2-1);
    }

    @Override
    public void generate(StringBuilder sb) {
        sb.insert(pos1-1, ind2char);
    }

    @Override
    public void parse(StringBuilder sb) {
        char actualChar = sb.charAt(pos1-1);
        if (actualChar != ind2char) {
            throw new ClientNumberInvalidException(ind2char, actualChar);
        } else {
            sb.deleteCharAt(pos1-1);
        }
    }
}
