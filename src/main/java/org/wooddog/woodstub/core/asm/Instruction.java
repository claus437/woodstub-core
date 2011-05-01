package org.wooddog.woodstub.core.asm;

import org.wooddog.woodstub.core.InternalErrorException;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.Arrays;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 24-04-11
 * Time: 20:00
 * To change this template use File | Settings | File Templates.
 */
public class Instruction  {
    private InstructionDefinition definition;
    protected int[] values;
    private int length;


    public Instruction(InstructionDefinition definition) {
        this.definition = definition;
        this.values = new int[definition.getParameterTypes().length];
    }

    public int getCode() {
        return definition.getCode();
    }

    public String getName() {
        return definition.getName();
    }

    public char[] getParameterTypes() {
        return definition.getParameterTypes();
    }

    public int[] getValues() {
        return values;
    }

    public void setValues(int[] values) {
        this.values = values;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
}
