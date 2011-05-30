package org.wooddog.woodstub.core.asm;

import org.wooddog.woodstub.core.InternalErrorException;

import java.io.DataOutputStream;
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
        char[] types;
        int length;

        length = 1;
        types = getParameterTypes();

        for (char t : types) {
            switch (t) {
                    case 'b':
                        length ++;
                        break;

                    case 'B':
                        length ++;
                        break;

                    case 's':
                        length += 2;
                        break;

                    case 'S':
                        length += 2;
                        break;

                    case 'I':
                        length += 4;
                        break;

                    default:
                        throw new InternalErrorException("unknown parameter type " + t + " for instruction code");
                }
        }

        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void write(DataOutputStream out) throws IOException {
        char[] parameterTypes;

        parameterTypes = getParameterTypes();

        out.writeByte(getCode());
        for (int i = 0; i < parameterTypes.length; i++) {
            switch (Character.toUpperCase(parameterTypes[i])) {
                case 'B':
                    out.writeByte(values[i]);
                    break;

                case 'S':
                    out.writeShort(values[i]);
                    break;

                case 'I':
                    out.writeInt(values[i]);

                    break;

                default:
                    throw new InternalErrorException("unknown parameter type " + parameterTypes[i] + " instruction code");
            }
        }
    }

    public String toString(int[] values) {
        String s = "";

        for (Object i : values) {
            s += i + ",";
        }

        return s;
    }

    private String toString(char[] values) {
        String s = "";

        for (Object i : values) {
            s += i + ",";
        }

        return s;
    }

}
