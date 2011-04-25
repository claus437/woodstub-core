package org.wooddog.woodstub.core.asm;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 24-04-11
 * Time: 19:57
 * To change this template use File | Settings | File Templates.
 */
public class ByteCodeReader {
    private static final Instruction[] INSTRUCTIONSET = new Instruction[255];
    private List<Instruction> instructions;

    static {
        Instruction instruction;
        String line;
        BufferedReader reader;
        InputStream stream;

        stream = ByteCodeReader.class.getClassLoader().getResourceAsStream("org/wooddog/woodstub/opcode-table.txt");

        reader = new BufferedReader(new InputStreamReader(stream));

        try {
            while((line = reader.readLine()) != null) {
                if (!line.isEmpty() && !line.startsWith("#")) {
                    instruction = Instruction.parse(line);
                    INSTRUCTIONSET[instruction.getOpCode()] = instruction;
                }
            }
        } catch (IOException x) {
            throw new InstantiationError("failed reading opcode table");
        }

    }

    public void read(DataInputStream stream, int length) throws IOException {
        byte opcode;
        int remaining;
        Instruction instruction;

        instructions = new ArrayList<Instruction>();
        remaining = length;

        while (remaining > 0) {
            opcode = stream.readByte();
            int index;

            index = opcode < 0 ? opcode + 256 : opcode;

            instruction = INSTRUCTIONSET[index];
            if (instruction == null) {
                Instruction in = new Instruction();
                in.opcode = index;
                in.values = new Object[0];

                instructions.add(in);
                System.out.println("UNK: " + opcode + " UNS." + index);
                remaining --;
                continue;
            }

            instruction.read(stream);
            instructions.add(instruction);
            remaining -= instruction.length();
        }
    }

    public void dump() {
        System.out.println("---------- dump ---------------");
        for (Instruction instruction : instructions) {
            System.out.println(instruction.toString());
        }
    }
}
