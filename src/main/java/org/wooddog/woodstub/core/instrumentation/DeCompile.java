package org.wooddog.woodstub.core.instrumentation;

import org.wooddog.woodstub.core.asm.Operation;
import org.wooddog.woodstub.core.asm.OperationReader;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: DENCBR
 * Date: 18-05-11
 * Time: 21:11
 * To change this template use File | Settings | File Templates.
 */
public class DeCompile {
    public static String asSource(byte[] code) throws IOException {
        DataInputStream stream;
        int length;
        Operation operation;
        StringBuffer buffer;


        stream = new DataInputStream(new ByteArrayInputStream(code));
        length = 0;


        buffer = new StringBuffer();

        while (length < code.length) {
            operation = OperationReader.read(stream);
            buffer.append(format(length, operation) + "\n");
            length += operation.size();
        }

        return buffer.toString();
    }

    private static String format(int address, Operation operation) {
        String source;

        source = address + " " + operation.getName() + " ";
        for (int value : operation.getValues()) {
            source += value + " ";
        }

        return source;
    }
}
