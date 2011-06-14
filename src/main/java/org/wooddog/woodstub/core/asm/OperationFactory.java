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
public class OperationFactory {
    public static final char POINTER_TYPE_CONSTANT = 'C';

    private static final int COLUMN_NAME = 0;
    private static final int COLUMN_CODE = 1;
    private static final int COLUMN_ARGUMENT_DATA_TYPE = 3;
    private static final int COLUMN_ARGUMENT_POINTER_TYPE = 4;

    private static final Map<String, OperationDefinition> OPERATION_NAME_MAP = new HashMap <String, OperationDefinition>();
    private static final Map<Integer, OperationDefinition> OPERATION_CODE_MAP = new HashMap <Integer, OperationDefinition>();

    static {
        OperationDefinition definition;
        String line;
        BufferedReader reader;
        InputStream stream;

        stream = OperationFactory.class.getClassLoader().getResourceAsStream("org/wooddog/woodstub/opcode-table.txt");

        reader = new BufferedReader(new InputStreamReader(stream));

        try {
            while((line = reader.readLine()) != null) {
                if (!line.isEmpty() && !line.startsWith("#")) {
                    definition = createOperationDefinition(line);

                    OPERATION_CODE_MAP.put(definition.getCode(), definition);
                    OPERATION_NAME_MAP.put(definition.getName(), definition);
                }
            }
        } catch (IOException x) {
            throw new InstantiationError("failed reading lines of operation code table");
        }
    }


    public static Instruction createInstruction(String name) {
        OperationDefinition def;

        def = OPERATION_NAME_MAP.get(name);
        if (def == null) {
            throw new InternalErrorException("unknown instruction code " + name);
        }

        return new Instruction(def);
    }

    public static Instruction createInstruction(int code) {
        OperationDefinition def;

        def = OPERATION_CODE_MAP.get(code);
        if (def == null) {
            throw new InternalErrorException("unknown instruction code " + code);
        }

        return new Instruction(def);
    }


    public static boolean isOperation(String name) {
        return OPERATION_NAME_MAP.containsKey(name);
    }

    private static OperationDefinition createOperationDefinition(String line) {
        String[] tokens;
        int code;
        String name;
        char[] parameterDataTypes;
        char[] parameterPointerTypes;

        tokens = line.split("[ \\t]+");

        name = getColumnValue(COLUMN_NAME, tokens);
        code = Integer.parseInt(getColumnValue(COLUMN_CODE, tokens), 10);

        parameterDataTypes = getColumnValue(COLUMN_ARGUMENT_DATA_TYPE, tokens).toCharArray();
        parameterPointerTypes = getColumnValue(COLUMN_ARGUMENT_POINTER_TYPE, tokens).toCharArray();

        try {
            return new OperationDefinition(code, name, parameterDataTypes, parameterPointerTypes);
        } catch (InternalErrorException x) {
            throw new InternalErrorException("unable to create operation definition for " + line + ", " + x.getMessage(), x);
        }
    }

    private static String getColumnValue(int column, String[] tokens) {
        return tokens.length > column ? tokens[column].trim() : "";
    }
}
