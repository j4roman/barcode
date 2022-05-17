package com.example.j4roman.barcode.service.utils.tasks.impl;

import com.example.j4roman.barcode.service.utils.tasks.Task;

/**
 * The task performs swapping of characters at ind1 and ind2 positions
 */
public class SwapSimpleTask implements Task {

    private final int pos1;
    private final int pos2;

    public SwapSimpleTask(int ind1, int ind2) {
        this.pos1 = ind1;
        this.pos2 = ind2;
    }

    @Override
    public void generate(StringBuilder sb) {
        char chr1 = sb.charAt(pos1 -1);
        char chr2 = sb.charAt(pos2-1);
        sb.setCharAt(pos1-1, chr2);
        sb.setCharAt(pos2-1, chr1);
    }

    @Override
    public void parse(StringBuilder sb) {
        generate(sb);
    }
}
