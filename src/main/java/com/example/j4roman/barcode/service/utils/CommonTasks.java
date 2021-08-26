package com.example.j4roman.barcode.service.utils;

import com.example.j4roman.barcode.persistance.entities.Action;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public final class CommonTasks {

    public static Task generate(Action action, String specValue) {
        String taskName = action.getTask().toUpperCase();
        if (!values().contains(taskName)) {
            throw new NoSuchTaskException(taskName);
        }
        return generate(Type.valueOf(taskName), action, specValue);
    }

    public static Task generate(Type type, Action action, String specValue) {
        final long ind1 = (action.getInd1() != null ? action.getInd1() : -1L);
        final long ind2 = (action.getInd2() != null ? action.getInd2() : -1L);
        final long count = (action.getCount() != null ? action.getCount() : -1L);
        final String cltValue = specValue;
        switch (type) {
            case SWP:
                return sbVal -> {
                    char chr1 = sbVal.charAt((int)ind1-1);
                    char chr2 = sbVal.charAt((int)ind2-1);
                    sbVal.setCharAt((int)ind1-1, chr2);
                    sbVal.setCharAt((int)ind2-1, chr1);
                };
            case DEL:
                return sbVal -> {
                    sbVal.delete((int)ind1 - 1, (int)(ind1+count-1));
                };
            case ADD_CLT:
                return sbVal -> {
                    sbVal.insert((int)ind1-1, cltValue.charAt((int)ind2-1));
                };
            case CHKNUM1:
                return sbVal -> {
                    int chkNum = 0;
                    for (int i = 0; i < sbVal.length(); i++) {
                        System.out.println(i);
                        int dig = Character.digit(sbVal.charAt(i), 10);
                        System.out.println(dig);
                        chkNum += dig * (i%2==0 ? 1 : 3);
                        System.out.println(chkNum);
                    }
                    chkNum = (chkNum / 10) * 10 - chkNum + 10;
                    sbVal.append(chkNum);
                };
            case INS:
                return sbVal -> {
                    sbVal.insert((int)ind1-1, (int)ind2);
                };
            default:
                return null;
        }
    }

    public static List<String> values() {
        return Arrays.stream(Type.values()).map(type -> type.toString()).collect(Collectors.toList());
    }

    public static interface Task {
        void exec(StringBuilder sbValue);
    }

    public static enum Type {
        SWP,
        DEL,
        ADD_CLT,
        CHKNUM1,
        INS
    }
}
