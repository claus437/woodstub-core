package org.wooddog.woodstub.core.asm;

import org.wooddog.woodstub.core.InternalErrorException;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 30-04-11
 * Time: 11:37
 * To change this template use File | Settings | File Templates.
 */
class OperationDefinition {
    private int code;
    private String name;
    private char[] parameterDataTypes;
    private char[] parameterPointerTypes;
    private int size;

    OperationDefinition(int code, String name, char[] parameterDataTypes, char[] parameterPointerTypes) {
        this.code = code;
        this.name = name;
        this.parameterDataTypes = parameterDataTypes;
        this.parameterPointerTypes = parameterPointerTypes;

        this.size = computeSize(parameterDataTypes) + 1;
    }

    int getCode() {
        return code;
    }

    String getName() {
        return name;
    }

    char[] getParameterDataTypes() {
        return parameterDataTypes;
    }

    char[] getParameterPointerTypes() {
        return parameterPointerTypes;
    }

    int getSize() {
        return this.size;
    }

    int computeSize(char[] types) {
        int size;

        size = 0;

        for (char t : types) {
            switch (Character.toUpperCase(t)) {
                case 'B':
                    size ++;
                    break;

                case 'S':
                    size += 2;
                    break;

                case 'I':
                    size += 4;
                    break;

                case 'L':
                    size = -1;
                    break;

                case 'V':
                    size = -1;
                    break;

                case 'W':
                    size = -1;
                    break;

                default:
                    throw new InternalErrorException("unknown type " + t);
            }
        }

        return size;
    }
}
