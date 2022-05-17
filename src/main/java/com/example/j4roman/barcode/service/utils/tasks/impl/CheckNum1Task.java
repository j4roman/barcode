package com.example.j4roman.barcode.service.utils.tasks.impl;

import com.example.j4roman.barcode.service.utils.tasks.Task;
import com.example.j4roman.barcode.service.utils.tasks.exceptions.CheckNumInvalidException;

/**
 * The task performs adding check number to the end using (1,3,1,3,1...) multipliers
 */
public class CheckNum1Task implements Task {

    @Override
    public void generate(StringBuilder sb) {
        sb.append(calcCheckNum(sb, sb.length()));
    }

    @Override
    public void parse(StringBuilder sb) {
        int chkNum = calcCheckNum(sb,sb.length() - 1);
        int realNum = Character.digit(sb.charAt(sb.length()-1), 10);
        if (chkNum != realNum) {
            throw new CheckNumInvalidException(chkNum, realNum);
        } else {
            sb.deleteCharAt(sb.length()-1);
        }
    }

    private static int calcCheckNum(StringBuilder sbVal, int calcLength) {
        int chkNum = 0;
        for (int i = 0; i < calcLength; i++) {
            int dig = Character.digit(sbVal.charAt(i), 10);
            chkNum += dig * (i % 2 == 0 ? 1 : 3);
        }
        chkNum = (chkNum / 10) * 10 - chkNum + 10;
        if (chkNum == 10) {
            chkNum = 0;
        }
        return chkNum;
    }
}
