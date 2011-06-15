package org.wooddog.woodstub.core.asm;

import org.wooddog.woodstub.core.InternalErrorException;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 15-06-11
 * Time: 16:42
 * To change this template use File | Settings | File Templates.
 */
public class VariableTable {
    private Map<String, Variable> variables;
    private int frameIndex;
    private Compiler compiler;

    private VariableTable(Compiler compiler, String argumentTypes) {
        this.compiler = compiler;

        variables = new HashMap<String, Variable>();

        for (int i = 0; i < argumentTypes.length(); i++) {
            addVariable(argumentTypes.charAt(i), "argument_" + i);
        }
    }

    public void store(char type, String name) {
        Variable variable;

        variable = addVariable(type, name);
        compiler.add(variable.opType + "store", variable.frameIndex);
    }

    public void load(String name) {
        Variable variable;

        variable = variables.get(name);
        compiler.add(variable.opType + "load", variable.frameIndex);
    }

    private Variable addVariable(char type, String name) {
        Variable variable;
        char opType;

        switch (type) {
            case 'Z':
                opType = 'i';
                break;

            case 'B':
                opType = 'i';
                break;

            case 'C':
                opType = 'i';
                break;

            case 'S':
                opType = 'i';
                break;

            case 'I':
                opType = 'i';
                break;

            case 'F':
                opType = 'f';
                break;

            case 'D':
                opType = 'd';
                break;

            case 'J':
                opType = 'l';
                break;

            case 'L':
                opType = 'a';
                break;

            default:
                throw new InternalErrorException("unknown type " + type);
        }

        variable = new Variable();
        variable.opType = opType;
        variable.frameIndex = frameIndex;
        variable.size = type == 'J' || type == 'D' ? 2 : 1;

        frameIndex += variable.size;

        variables.put(name, variable);

        return variable;
    }

    private class Variable {
        char opType;
        int size;
        int frameIndex;
    }
}
