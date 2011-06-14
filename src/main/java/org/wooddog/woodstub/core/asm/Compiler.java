package org.wooddog.woodstub.core.asm;

import org.wooddog.woodstub.core.UnImplementedFeatureException;
import org.wooddog.woodstub.core.instrumentation.ConstantPool;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: dencbr
 * Date: 30-05-11
 * Time: 16:55
 * To change this template use File | Settings | File Templates.
 */
public class Compiler {
    private CodeTable codeTable;
    private LabelTable labels;
    private ConstantPool pool;

    public Compiler(ConstantPool pool) {
        this.pool = pool;
        this.labels = new LabelTable();
        this.codeTable = new CodeTable();
    }

    public void setLabel(String label) {
        labels.setAddress(label, codeTable.getAddress());
    }

    public void add(String operationName, Object... args) {
        Instruction instruction;
        int[] values;

        if (isJvmOperationConstant(operationName, args)) {
            instruction = OperationFactory.createInstruction(operationName + "_" + args[0]);
        } else {
            instruction = OperationFactory.createInstruction(operationName);
            values = resolveValues(instruction, codeTable.getAddress(), args);
            instruction.setValues(values);
        }

        codeTable.add(instruction);
    }

    public byte[] compile() throws IOException {
        OperationWriter operationWriter;
        ByteArrayOutputStream stream;

        labels.setAddress("END", codeTable.getAddress());

        stream = new ByteArrayOutputStream();
        operationWriter = new OperationWriter(new DataOutputStream(stream));

        codeTable.write(operationWriter);

        operationWriter.close();

        return stream.toByteArray();
    }

    private boolean isJvmOperationConstant(String operationName, Object[] args) {
        return args.length == 1 && OperationFactory.isOperation(operationName + "_" + args[0]);
    }

    private int[] resolveValues(Instruction instruction, int address, Object[] args) {
        char[] info;
        int[] values;
        String value;
        int poolIndex;

        values = new int[instruction.getParameterTypes().length];

        info = instruction.getParameterInfo();
        for (int i = 0; i < info.length; i++) {
            if (info[i] == 'V') {
                values[i] = (Integer) args[i];
            }

            if (info[i] == 'F') {
                values[i] = (Integer) args[i];
            }

            if (info[i] == 'A') {
                if (args[i] instanceof String) {
                    labels.map(instruction, address, (String) args[i], i);
                } else {
                    values[i] = (Integer) args[i];
                }
            }

            if (info[i] == 'C') {
                if (args[i] instanceof String) {
                    poolIndex = pool.addString((String) args[i]);
                    values[i] = poolIndex;
                } else {
                    throw new UnImplementedFeatureException("pool constants (C) can only be resolved to a string not " + args[i].getClass().getSimpleName());
                }
            }

            if (info[i] == 'M') {
                value = (String) args[i];
                poolIndex = pool.addMethodRef(getClassName(value), getMethodName(value), getSignature(value));
                values[i] = poolIndex;
            }

            if (info[i] == 'K') {
                if (args[i] instanceof  String) {
                    poolIndex = pool.addClass((String) args[i]);
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
}
