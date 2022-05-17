package com.example.j4roman.barcode.service.utils.tasks.impl;

import com.example.j4roman.barcode.service.utils.tasks.Task;
import com.example.j4roman.barcode.service.utils.tasks.exceptions.FixedNumNotValidException;

/**
 * The task performs inserting of fixed digit with value ind2 into position ind1
 */
public class InsertDigitTask implements Task {

    private final int pos1;
    private final char fixedDigit;

    public InsertDigitTask(int ind1, int ind2) {
        this.pos1 = ind1;
        this.fixedDigit = String.valueOf(ind2).charAt(0);
    }

    @Override
    public void generate(StringBuilder sb) {
        sb.insert(pos1-1, fixedDigit);
    }

    @Override
    public void parse(StringBuilder sb) {
        char actualDigit = sb.charAt(pos1-1);
        if (actualDigit != fixedDigit) {
            throw new FixedNumNotValidException(fixedDigit, actualDigit);
        } else {
            sb.deleteCharAt(pos1-1);
        }
    }
}
