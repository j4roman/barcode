package com.example.j4roman.barcode.service.utils;

import com.example.j4roman.barcode.persistance.entities.Action;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * List of common tasks and methods to generate them
 *
 */
public final class CommonTasks {

    /**
     * The method generates {@link Task} implementation that performs
     * simple String transformation.
     *
     * Enum {@link Type} contains allowed operations.
     *
     * Generates value to barcode step-unit
     *
     * @param action set of inner values to perform operation (task)
     * @param specValue special client specific value used in operations (tasks)
     * @return step unit in transformation process
     */
    public static Task generate(Action action, String specValue) {
        return generate(action, specValue, false);
    }

    /**
     * The method generates {@link Task} implementation that performs
     * simple String transformation.
     *
     * Enum {@link Type} contains allowed operations.
     *
     * @param action set of inner values to perform operation (task)
     * @param specValue special client specific value used in operations (tasks)
     * @param isParse if <code>true</code> then operation (task) generates barcode-to-value
     *                transformation step, else - value-to-barcode
     * @return step unit in transformation process
     */
    public static Task generate(Action action, String specValue, boolean isParse) {
        String taskName = action.getTask().toUpperCase();
        if (!values().contains(taskName)) {
            throw new NoSuchTaskException(taskName);
        }
        return generate(Type.valueOf(taskName), action, specValue, isParse);
    }

    /**
     * The method generates {@link Task} implementation that performs
     * simple String transformation.
     *
     * Enum {@link Type} contains allowed operations.
     *
     * @param type performed operation (task)
     * @param action set of inner values to perform operation (task)
     * @param specValue special client specific value used in operations (tasks)
     * @param isParse if <code>true</code> then operation (task) generates barcode-to-value
     *                transformation step, else - value-to-barcode
     * @return step unit in transformation process
     */
    public static Task generate(Type type, Action action, String specValue, boolean isParse) {
        final int ind1 = (int)(action.getInd1() != null ? action.getInd1() : -1L);
        final int ind2 = (int)(action.getInd2() != null ? action.getInd2() : -1L);
        final int count = (int)(action.getCount() != null ? action.getCount() : -1L);
        final String cltValue = specValue;
        switch (type) {
            case SWP:
                // swaps digits at ind1 and ind2 positions
                return sbVal -> {
                    char chr1 = sbVal.charAt(ind1-1);
                    char chr2 = sbVal.charAt(ind2-1);
                    sbVal.setCharAt(ind1-1, chr2);
                    sbVal.setCharAt(ind2-1, chr1);
                };
            case ADD_CLT:
                // inserts digit from client's specValue at position ind2
                // into position ind1 of string
                if (!isParse) {
                    return sbVal -> {
                        sbVal.insert(ind1-1, cltValue.charAt(ind2-1));
                    };
                } else {
                    return sbVal -> {
                        int val = Character.digit(sbVal.charAt(ind1-1), 10);
                        int expectedVal = Character.digit(cltValue.charAt(ind2-1), 10);
                        if (val != expectedVal) {
                            throw new ClientNumberInvalidException(expectedVal, val);
                        } else {
                            sbVal.deleteCharAt(ind1-1);
                        }
                    };
                }
            case CHKNUM1:
                // calculates check number and add it to the end
                if (!isParse) {
                    return sbVal -> {
                        sbVal.append(calcCheckNum1(sbVal, sbVal.length()));
                    };
                } else {
                    return sbVal -> {
                        int chkNum = calcCheckNum1(sbVal,sbVal.length() - 1);
                        int realNum = Character.digit(sbVal.charAt(sbVal.length()-1), 10);
                        if (chkNum != realNum) {
                            throw new CheckNumInvalidException(chkNum, realNum);
                        } else {
                            sbVal.deleteCharAt(sbVal.length()-1);
                        }
                    };
                }
            case INS:
                // inserts fixed digit ind2 into position ind1
                if (!isParse) {
                    return sbVal -> {
                        sbVal.insert(ind1-1, ind2);
                    };
                } else {
                    return sbVal -> {
                        int val = Character.digit(sbVal.charAt(ind1-1), 10);
                        int expectedVal = ind2;
                        if (val != expectedVal) {
                            throw new FixedNumNotValidException(expectedVal, val);
                        } else {
                            sbVal.deleteCharAt(ind1-1);
                        }
                    };
                }
            default:
                return null;
        }
    }

    /**
     * Get list of allowed operations (tasks)
     * @return list of allowed operations (tasks)
     */
    public static List<String> values() {
        return Arrays.stream(Type.values()).map(type -> type.toString()).collect(Collectors.toList());
    }

    /**
     * Step-unit of transformation process
     */
    public static interface Task {
        void exec(StringBuilder sbValue);
    }

    public static enum Type {
        SWP,
        ADD_CLT,
        CHKNUM1,
        INS
    }

    /**
     * Calculates check number
     *
     * @param sbVal calculated string value
     * @param calcLength the length of calculated part of string
     * @return check number
     */
    private static int calcCheckNum1(StringBuilder sbVal, int calcLength) {
        int chkNum = 0;
        for (int i = 0; i < calcLength; i++) {
            System.out.println(i);
            int dig = Character.digit(sbVal.charAt(i), 10);
            System.out.println(dig);
            chkNum += dig * (i % 2 == 0 ? 1 : 3);
            System.out.println(chkNum);
        }
        chkNum = (chkNum / 10) * 10 - chkNum + 10;
        if (chkNum == 10) {
            chkNum = 0;
        }
        return chkNum;
    }
}
