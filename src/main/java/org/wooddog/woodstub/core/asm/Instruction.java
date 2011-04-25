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
public class Instruction {
    protected int opcode;
    private String name;
    private char[] parameterTypes;
    protected Object[] values;
    private int length;

    public int getOpCode() {
        return opcode;
    }

    public String getName() {
        return name;
    }

    public Object[] getValues() {
        return values;
    }

    public void read(DataInputStream stream) throws IOException {
        for (int i = 0; i < parameterTypes.length; i ++) {
            switch (Character.toUpperCase(parameterTypes[i])) {
                case 'B':
                    values[i] = Byte.valueOf(stream.readByte());
                    break;

                case 'S':
                    values[i] = Short.valueOf(stream.readShort());
                    break;

                case 'I':
                    values[i] = Integer.valueOf(stream.readInt());
                    break;

                case 'V':
                    values[i] = Byte.valueOf(stream.readByte());
                    break;

                default:
                    throw new InternalErrorException("opcode: " + name + " has unknown parameter type " + Character.toUpperCase(parameterTypes[i]));
            }
        }
    }

    public int length() {
        return length;
    }

    public static Instruction parse(String line) {
        Instruction instruction;
        String[] tokens;

        tokens = line.split("\\W+");

        instruction = new Instruction();
        instruction.name = tokens[0].trim();
        instruction.opcode = Integer.parseInt(tokens[1], 10);

        if (tokens.length > 3) {
            instruction.parameterTypes = tokens[3].trim().toCharArray();
        } else {
            instruction.parameterTypes = new char[0];
        }

        instruction.values = new Object[instruction.parameterTypes.length];
        instruction.length = 1;

        for (char type : instruction.parameterTypes) {
            switch (Character.toUpperCase(type)) {
                case 'B':
                    instruction.length ++;
                    break;

                case 'S':
                    instruction.length += 2;
                    break;

                case 'I':
                    instruction.length += 4;
                    break;

                case 'V':
                    break;

                default:
                    throw new InternalErrorException("opcode: " + instruction.name + " has unknown parameter type " + Character.toUpperCase(type));
            }
        }

        return instruction;
    }

    public String toString() {
        return opcode + " " + name + " " + Arrays.asList(values);
    }
}
