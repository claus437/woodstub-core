package org.wooddog.woodstub.core.asm;

import org.wooddog.woodstub.core.UnImplementedFeatureException;
import org.wooddog.woodstub.core.instrumentation.ConstantPool;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: dencbr
 * Date: 30-05-11
 * Time: 16:55
 * To change this template use File | Settings | File Templates.
 */
public class Compiler {
    private List<Operation> operations;
    private List<Instruction> codeTable;
    private Map<String, Integer> labels;
    private Map<Instruction, String> postFixLabels;
    private ConstantPool pool;

    public Compiler(ConstantPool pool) {
        this.pool = pool;
        this.operations = new ArrayList<Operation>();
        this.codeTable = new ArrayList<Instruction>();
        this.labels = new HashMap<String, Integer>();
        this.postFixLabels = new HashMap<Instruction, String>();
    }


    public void add(String operation, Object... args) {
        if ("label".equals(operation)) {
            labels.put((String) args[0], operations.size());
            return;
        }

        if ("iconst".equals(operation)) {
            operations.add(new Operation("iconst_" + args[0].toString()));
            return;
        }

        if ("aload".equals(operation)) {
            if ((Integer) args[0] < 5) {
                operations.add(new Operation("aload_" + args[0].toString()));
            } else {
                operations.add(new Operation("aload", new Integer[]{(Integer) args[0]}));
            }
            return;
        }

        operations.add(new Operation(operation,  args));

    }


    public byte[] compile() throws IOException {
        int address;

        InstructionDefinition def;
        Instruction instruction;
        int[] values;

        address = 0;

        for (Operation operation : operations) {
            if ("label".equals(operation.operation)) {
                labels.put((String) operation.args[0], address);
                continue;
            }

            def = CodeTable.getInstructionDefinition(operation.operation);

            instruction = new Instruction(def);
            values = resolveValues(instruction, operation);
            instruction.setValues(values);

            address += instruction.getLength();
            codeTable.add(instruction);
        }

        return compile(codeTable);
    }

    private int[] resolveValues(Instruction instruction, Operation operation) {
        char[] info;
        int[] values;
        String value;
        int poolIndex;

        values = new int[instruction.getParameterTypes().length];

        info = instruction.getParameterInfo();
        for (int i = 0; i < info.length; i++) {
            if (info[i] == 'V') {
                values[i] = (Integer) operation.args[i];
            }

            if (info[i] == 'F') {
                values[i] = (Integer) operation.args[i];
            }

            if (info[i] == 'A') {
                if (operation.args[i] instanceof String) {
                    postFixLabels.put(instruction, (String) operation.args[i]);
                } else {
                    values[i] = (Integer) operation.args[i];
                }
            }

            if (info[i] == 'C') {
                if (operation.args[i] instanceof String) {
                    poolIndex = pool.addString((String) operation.args[i]);
                    values[i] = poolIndex;
                } else {
                    throw new UnImplementedFeatureException("pool constants (C) can only be resolved to a string not " + operation.args[i].getClass().getSimpleName());
                }
            }

            if (info[i] == 'M') {
                value = (String) operation.args[i];
                poolIndex = pool.addMethodRef(getClassName(value), getMehtodName(value), getSignature(value));
                values[i] = poolIndex;
            }

            if (info[i] == 'K') {
                if (operation.args[i] instanceof  String) {
                    poolIndex = pool.addClass((String) operation.args[i]);
                    values[i] = poolIndex;
                }
            }
        }

        return values;
    }

    private String getClassName(String qualifiedMethod) {
        return qualifiedMethod.substring(0, qualifiedMethod.indexOf("#"));
    }

    private String getMehtodName(String qualifiedMethod) {
        return qualifiedMethod.substring(qualifiedMethod.indexOf("#") + 1, qualifiedMethod.indexOf(")") + 1);
    }

    private String getSignature(String qualifiedMethod) {
        return qualifiedMethod.substring(qualifiedMethod.indexOf(")") + 1);
    }

    private byte[] compile(List<Instruction> codeTable) throws IOException {
        ByteArrayOutputStream stream;
        DataOutputStream dataStream;

        resolveLabels();

        stream = new ByteArrayOutputStream();
        dataStream = new DataOutputStream(stream);

        for (Instruction instruction : codeTable) {
            instruction.write(dataStream);
        }

        dataStream.close();

        return stream.toByteArray();
    }


    private void resolveLabels() {
        int[] addresses;
        int address;
        int instructionAddress;
        int index;
        String label;

        addresses = new int[operations.size() + 1];
        addresses[0] = 0;
        for (int i = 1; i < addresses.length; i++) {
            addresses[i] = addresses[i - 1] + codeTable.get(i - 1).getLength();
        }

        labels.put("END", operations.size());

        for (Instruction instruction : postFixLabels.keySet()) {
            label = postFixLabels.get(instruction);
            index = labels.get(label);
            address = addresses[index];

            instructionAddress = codeTable.indexOf(instruction);
            instructionAddress = addresses[instructionAddress];

            instruction.setValues(new int[]{address - instructionAddress});
        }
    }

    private class Operation {
        String operation;
        Object[] args;

        public Operation(String operation) {
            this.operation = operation;
        }

        private Operation(String operation, Object[] args) {
            this.operation = operation;
            this.args = args;
        }
    }
}
