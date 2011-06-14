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

            remaining = remaining - operation.size();
        }
    }

    public static Operation read(DataInputStream stream) throws IOException {
        int code;
        Operation operation;
        char[] parameterTypes;

        code = stream.readUnsignedByte();
        operation = OperationFactory.createOperation(code);

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
}
