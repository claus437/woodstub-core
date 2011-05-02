package org.wooddog.woodstub.core.instrumentation;


import org.wooddog.woodstub.core.asm.InstructionReader;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 25-04-11
 * Time: 16:44
 * To change this template use File | Settings | File Templates.
 */
public class AttributeCode implements Attribute {
    private List<TableException> exceptions;
    private List<Attribute> attributes;

    private int poolIndex;
    private int maxStack;
    private int maxLocals;
    private byte[] code;

    public void setConstantPoolIndex(int index) {
        this.poolIndex = index;
    }

    public int getPoolIndex() {
        return poolIndex;
    }

    public void setPoolIndex(int poolIndex) {
        this.poolIndex = poolIndex;
    }

    public int getMaxStack() {
        return maxStack;
    }

    public void setMaxStack(int maxStack) {
        this.maxStack = maxStack;
    }

    public int getMaxLocals() {
        return maxLocals;
    }

    public void setMaxLocals(int maxLocals) {
        this.maxLocals = maxLocals;
    }

    public byte[] getCode() {
        return code;
    }

    public void setCode(byte[] code) {
        this.code = code;
    }

    public void read(ConstantPool constantPool, DataInputStream stream) throws IOException {
        int codeLength;
        int exceptionCount;
        int attributeCount;
        int length;
        TableException exception;

        length = stream.readInt();
        maxStack = stream.readUnsignedShort();
        maxLocals = stream.readUnsignedShort();

        codeLength = stream.readInt();
        code = new byte[codeLength];
        //stream.readFully(code);

        InstructionReader ir;
        ir = new InstructionReader();
        ir.readBlock(stream, codeLength);

        exceptionCount = stream.readUnsignedShort();
        exceptions = new ArrayList<TableException>();

        for (int i = 0; i < exceptionCount; i++) {
            exception = new TableException();
            exception.read(stream);
            exceptions.add(exception);
        }

        attributeCount = stream.readUnsignedShort();
        attributes = new ArrayList<Attribute>();

        for (int i = 0; i < attributeCount; i++) {
            attributes.add(AttributeFactory.create(constantPool, stream));
        }
    }

    public void write(ConstantPool constantPool, DataOutputStream stream) throws IOException {
        stream.writeShort(poolIndex);
        stream.writeInt(getLength() - 6);
        stream.writeShort(maxStack);
        stream.writeShort(maxLocals);
        stream.writeInt(code.length);
        stream.write(code);

        stream.writeShort(exceptions.size());
        for (TableException exception : exceptions) {
            exception.write(stream);
        }

        stream.writeShort(attributes.size());
        for (Attribute attribute : attributes) {
            attribute.write(constantPool, stream);
        }
    }

    public int getLength() {
        int total;

        total = 18;

        for (Attribute attribute : attributes) {
            total += attribute.getLength();
        }

        for (TableException exception : exceptions) {
            total += exception.getLength();
        }

        total += code.length;

        return total;
    }

    public String getName() {
        return "Code";
    }
}
