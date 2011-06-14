package org.wooddog.woodstub.core.asm;

import org.wooddog.woodstub.core.InternalErrorException;

import java.io.Closeable;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 13-06-11
 * Time: 19:37
 * To change this template use File | Settings | File Templates.
 */
public class OperationWriter implements Closeable {
    DataOutputStream stream;

    public OperationWriter(DataOutputStream stream) {
        this.stream = stream;
    }

    public void write(Operation operation) throws IOException {
        char[] parameterTypes;
        int[] values;

        values = operation.getValues();

        parameterTypes = operation.getParameterTypes();

        stream.writeByte(operation.getCode());
        for (int i = 0; i < parameterTypes.length; i++) {
            switch (Character.toUpperCase(parameterTypes[i])) {
                case 'B':
                    stream.writeByte(values[i]);
                    break;

                case 'S':
                    stream.writeShort(values[i]);
                    break;

                case 'I':
                    stream.writeInt(values[i]);
                    break;

                default:
                    throw new InternalErrorException("unknown parameter type " + parameterTypes[i] + " operation code");
            }
        }
    }

    public void close() throws IOException {
        stream.close();
    }
}
