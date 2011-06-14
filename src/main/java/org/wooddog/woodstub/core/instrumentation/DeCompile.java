package org.wooddog.woodstub.core.instrumentation;

import org.wooddog.woodstub.core.asm.Instruction;
import org.wooddog.woodstub.core.asm.InstructionReader;

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
        Instruction instruction;
        StringBuffer buffer;


        stream = new DataInputStream(new ByteArrayInputStream(code));
        length = 0;


        buffer = new StringBuffer();

        while (length < code.length) {
            instruction = InstructionReader.read(stream);
            buffer.append(format(length, instruction) + "\n");
            length += instruction.getLength();
        }

        return buffer.toString();
    }

    private static String format(int address, Instruction instruction) {
        String source;

        source = address + " " + instruction.getName() + " ";
        for (int value : instruction.getValues()) {
            source += value + " ";
        }

        return source;
    }
}
