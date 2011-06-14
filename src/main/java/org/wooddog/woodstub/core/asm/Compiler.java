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
        Operation operation;
        int[] values;

        if (isJvmOperationConstant(operationName, args)) {
            operation = OperationFactory.createOperation(operationName + "_" + args[0]);
        } else {
            operation = OperationFactory.createOperation(operationName);
            values = resolveValues(operation, codeTable.getAddress(), args);
            operation.setValues(values);
        }

        codeTable.add(operation);
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

    private int[] resolveValues(Operation operation, int address, Object[] args) {
        char[] info;
        int[] values;
        String value;
        int poolIndex;

        values = new int[operation.getParameterTypes().length];

        info = operation.getParameterInfo();
        for (int i = 0; i < info.length; i++) {
            switch(info[i]) {
                case 'V':
                    values[i] = (Integer) args[i];
                    break;

                case 'F':
                    values[i] = (Integer) args[i];
                    break;

                case 'A':
                    if (args[i] instanceof String) {
                        labels.map(operation, address, (String) args[i], i);
                    } else {
                        values[i] = (Integer) args[i];
                    }
                    break;

                case 'C':
                    if (args[i] instanceof String) {
                        poolIndex = pool.addString((String) args[i]);
                        values[i] = poolIndex;
                    } else {
                        throw new UnImplementedFeatureException("pool constants (C) can only be resolved to a string not " + args[i].getClass().getSimpleName());
                    }
                    break;

                case 'M':
                    value = (String) args[i];
                    poolIndex = pool.addMethodRef(getClassName(value), getMethodName(value), getSignature(value));
                    values[i] = poolIndex;
                    break;

                case 'K':
                    if (args[i] instanceof  String) {
                        poolIndex = pool.addClass((String) args[i]);
                        values[i] = poolIndex;
                    }
                    break;
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
