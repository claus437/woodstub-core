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
    private LabelTable labels;
    private ConstantPool pool;

    public Compiler(ConstantPool pool) {
        this.pool = pool;
        this.operations = new ArrayList<Operation>();
        this.codeTable = new ArrayList<Instruction>();
        this.labels = new LabelTable();
    }

    public void add(String operation, Object... args) {
        operations.add(new Operation(operation,  args));
    }

    public byte[] compile() throws IOException {
        int address;

        Instruction instruction;
        int[] values;

        address = 0;

        for (Operation operation : operations) {
            if ("label".equals(operation.operation)) {
                labels.setAddress((String) operation.args[0], address);
                continue;
            }

            if (isJvmOperationConstant(operation)) {
                instruction = CodeTable.createInstruction(operation.operation + "_" + operation.args[0]);
            } else {
                instruction = CodeTable.createInstruction(operation.operation);
                values = resolveValues(instruction, address, operation);
                instruction.setValues(values);
            }

            address += instruction.getLength();
            codeTable.add(instruction);
        }

        return compile(codeTable);
    }

    private boolean isJvmOperationConstant(Operation operation) {
        return operation.args.length == 1 && CodeTable.isOperation(operation.operation + "_" + operation.args[0]);
    }

    private int[] resolveValues(Instruction instruction, int address, Operation operation) {
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
                    labels.map(instruction, address, (String) operation.args[i], i);
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
                poolIndex = pool.addMethodRef(getClassName(value), getMethodName(value), getSignature(value));
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

    private String getMethodName(String qualifiedMethod) {
        return qualifiedMethod.substring(qualifiedMethod.indexOf("#") + 1, qualifiedMethod.indexOf(")") + 1);
    }

    private String getSignature(String qualifiedMethod) {
        return qualifiedMethod.substring(qualifiedMethod.indexOf(")") + 1);
    }

    private byte[] compile(List<Instruction> codeTable) throws IOException {
        OperationWriter operationWriter;
        ByteArrayOutputStream stream;
        int size;

        size = 0;

        for (Instruction instruction : codeTable) {
            size += instruction.getLength();
        }

        labels.setAddress("END", size);

        stream = new ByteArrayOutputStream();
        operationWriter = new OperationWriter(new DataOutputStream(stream));

        for (Instruction instruction : codeTable) {
            operationWriter.write(instruction);
        }

        operationWriter.close();

        return stream.toByteArray();
    }

    private class Operation {
        String operation;
        Object[] args;

        private Operation(String operation, Object[] args) {
            this.operation = operation;
            this.args = args;
        }
    }
}
