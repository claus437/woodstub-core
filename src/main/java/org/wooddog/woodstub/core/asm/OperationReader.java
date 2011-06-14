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
public class OperationReader {
    private List<Operation> operations;


    public List<Operation> getOperations() {
        return operations;
    }

    public void readBlock(DataInputStream stream, int length) throws IOException {
        int remaining;
        Operation operation;

        operations = new ArrayList<Operation>();
        remaining = length;

        while (remaining > 0) {
            operation = read(stream);
            operations.add(operation);

            remaining = remaining - operation.getLength();
        }
    }

    public static Operation read(DataInputStream stream) throws IOException {
        int code;
        Operation operation;
        char[] parameterTypes;

        code = stream.readUnsignedByte();
        operation = OperationFactory.createInstruction(code);

        parameterTypes = operation.getParameterTypes();

        for (int i = 0; i < parameterTypes.length; i++) {
            switch (parameterTypes[i]) {
                case 'b':
                    operation.getValues()[i] = stream.readByte();
                    break;

                case 'B':
                    operation.getValues()[i] = stream.readUnsignedByte();
                    break;

                case 's':
                    operation.getValues()[i] = stream.readShort();
                    break;

                case 'S':
                    operation.getValues()[i] = stream.readUnsignedShort();
                    break;

                case 'I':
                    operation.getValues()[i] = stream.readInt();
                    break;

                case 'V':
                    operation.getValues()[i] = stream.readUnsignedByte();
                    break;

                case 'L':
                    break;

                default:
                    throw new InternalErrorException("unknown parameter type " + parameterTypes[i]);
            }
        }

        return operation;
    }

    private static int readTableSwitch(Operation operation, DataInputStream stream) throws IOException {
        int defaultAddress;
        int count;
        int[] values;
        int length;

        length = 0;

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

        operation.setValues(values);
        return length;
    }

    public void dump() {
        int pos;
        pos = 0;
        System.out.println("--- CODE DUMP -");
        for (int i = 0; i < operations.size(); i++) {
            System.out.print("#" + i + "/" + pos + " " + operations.get(i).getCode() + " " + operations.get(i).getName() + " ");
            for (Integer value : operations.get(i).values) {
                System.out.print(value + " ");
            }

            pos += operations.get(i).getLength();
            System.out.println();
        }
    }


}
