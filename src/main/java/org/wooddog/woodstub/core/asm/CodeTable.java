package org.wooddog.woodstub.core.asm;

import org.wooddog.woodstub.core.InternalErrorException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 30-04-11
 * Time: 11:35
 * To change this template use File | Settings | File Templates.
 */
public class CodeTable {
    private static final InstructionDefinition[] TABLE = new InstructionDefinition[255];
    private static final Map<String, InstructionDefinition> MAP = new HashMap <String, InstructionDefinition>();

    static {
        InstructionDefinition entry;
        String line;
        BufferedReader reader;
        InputStream stream;

        stream = CodeTable.class.getClassLoader().getResourceAsStream("org/wooddog/woodstub/opcode-table.txt");

        reader = new BufferedReader(new InputStreamReader(stream));

        try {
            while((line = reader.readLine()) != null) {
                if (!line.isEmpty() && !line.startsWith("#")) {
                    entry = createTableEntry(line);
                    TABLE[entry.getCode()] = entry;
                    MAP.put(entry.getName(), entry);
                }
            }
        } catch (IOException x) {
            throw new InstantiationError("failed reading opcode table");
        }
    }

    public static InstructionDefinition getInstructionDefinition(String name) {
        if (MAP.get(name) == null) {
            throw new InternalErrorException("unknown instruction code " + name);
        }

        return MAP.get(name);
    }

    public static InstructionDefinition getInstructionDefinition(int code) {
        if (TABLE[code] == null) {
            throw new InternalErrorException("unknown instruction code " + code);
        }

        return TABLE[code];
    }


    private static InstructionDefinition createTableEntry(String line) {
        InstructionDefinition entry;
        String[] tokens;

        tokens = line.split("\\W+");

        entry = new InstructionDefinition();
        entry.setName(tokens[0].trim());
        entry.setCode(Integer.parseInt(tokens[1], 10));

        if (tokens.length > 3) {
            entry.setParameterTypes(tokens[3].trim().toCharArray());
        } else {
            entry.setParameterTypes(new char[0]);
        }

        return entry;
    }
}
