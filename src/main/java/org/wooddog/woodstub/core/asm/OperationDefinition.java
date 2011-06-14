package org.wooddog.woodstub.core.asm;

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

        this.size = Util.computeSize(parameterDataTypes) + 1;
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
}
