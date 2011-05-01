package org.wooddog.woodstub.core.asm;

import org.wooddog.woodstub.core.InternalErrorException;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 30-04-11
 * Time: 11:45
 * To change this template use File | Settings | File Templates.
 */
public class InstructionReader {
    private List<Instruction> instructions;



    public void readBlock(DataInputStream stream, int length) throws IOException {
        int remaining;
        Instruction instruction;

        instructions = new ArrayList<Instruction>();
        remaining = length;

        try {
            while (remaining > 0) {
                instruction = read(stream);
                instructions.add(instruction);

                remaining = remaining - instruction.getLength();
            }
        } finally {
            System.out.println("length: " + length + " / remaing " + remaining);
            dump();

        }
    }

    public static Instruction read(DataInputStream stream) throws IOException {
        int code;
        int length;
        InstructionDefinition definition;
        Instruction instruction;
        char[] parameterTypes;

        code = stream.readUnsignedByte();
        definition = CodeTable.getInstructionDefinition(code);

        instruction = new Instruction(definition);
        parameterTypes = instruction.getParameterTypes();
        length = 1;

        for (int i = 0; i < parameterTypes.length; i++) {
            switch (parameterTypes[i]) {
                case 'b':
                    instruction.getValues()[i] = stream.readByte();
                    length ++;
                    break;

                case 'B':
                    instruction.getValues()[i] = stream.readUnsignedByte();
                    length ++;
                    break;

                case 's':
                    instruction.getValues()[i] = stream.readShort();
                    length += 2;
                    break;

                case 'S':
                    instruction.getValues()[i] = stream.readUnsignedShort();
                    length += 2;
                    break;

                case 'I':
                    instruction.getValues()[i] = stream.readInt();
                    length += 4;
                    break;

                case 'V':
                    instruction.getValues()[i] = stream.readUnsignedByte();
                    length ++;
                    break;

                case 'L':
                    length += readTableSwitch(instruction, stream);
                    break;

                default:
                    throw new InternalErrorException("unknown parameter type " + parameterTypes[i]);
            }
        }

        instruction.setLength(length);


        return instruction;
    }

    private static int readTableSwitch(Instruction instruction, DataInputStream stream) throws IOException {
        int defaultAddress;
        int count;
        int[] values;
        int length;

        length = 0;

        System.out.println("nop: " + stream.readUnsignedByte());
        System.out.println("nop: " + stream.readUnsignedByte());
        System.out.println("nop: " + stream.readUnsignedByte());
        length += 3;


        defaultAddress = stream.readInt();
        length += 4;
        count = stream.readInt();
        length += 4;

        values = new int[(count * 2) + 2];
        values[0] = defaultAddress;
        values[1] = count;

        for (int i = 0; i < count * 2; i++) {
            values[i + 2] = stream.readInt();
            length += 4;
        }

        instruction.setValues(values);
        return length;
    }

    public void dump() {
        int pos;
        pos = 0;
        System.out.println("--- CODE DUMP -");
        for (int i = 0; i < instructions.size(); i++) {
            System.out.print("#" + i + "/" + pos + " " + instructions.get(i).getCode() + " " + instructions.get(i).getName() + " ");
            for (Integer value : instructions.get(i).values) {
                System.out.print(value + " ");
            }

            pos += instructions.get(i).getLength();
            System.out.println();
        }
    }


}
