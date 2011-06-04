package org.wooddog.woodstub.core.asm;

import org.wooddog.woodstub.core.InternalErrorException;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 30-04-11
 * Time: 11:37
 * To change this template use File | Settings | File Templates.
 */
public class InstructionDefinition {
    private int code;
    private String name;
    private char[] parameterTypes;
    private char[] parameterInfo;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public char[] getParameterTypes() {
        return parameterTypes;
    }

    public void setParameterTypes(char[] parameterTypes) {
        this.parameterTypes = parameterTypes;
    }

    public char[] getParameterInfo() {
        return parameterInfo;
    }

    public void setParameterInfo(char[] parameterInfo) {
        this.parameterInfo = parameterInfo;
    }
}
