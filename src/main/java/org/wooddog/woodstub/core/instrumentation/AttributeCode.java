package org.wooddog.woodstub.core.instrumentation;


import org.wooddog.woodstub.core.Converter;
import org.wooddog.woodstub.core.asm.ByteCodeReader;

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

    // need to be long ???

    public void setConstantPoolIndex(int index) {
        this.poolIndex = index;
    }

    public void read(ConstantPool constantPool, DataInputStream stream) throws IOException {
        int codeLength;
        int exceptionCount;
        int attributeCount;
        int length;
        TableException exception;

        System.out.println("ATT LEN: " + stream.readInt());
        maxStack = Converter.asUnsigned(stream.readShort());
        maxLocals = Converter.asUnsigned(stream.readShort());

        codeLength = stream.readInt();
        code = new byte[codeLength];

        for (int i = 0; i < codeLength; i++) {
            code[i] = stream.readByte();
        }
        /*
        ByteCodeReader br;
        br = new ByteCodeReader();
        br.read(stream, codeLength);
        br.dump();
        */
        exceptionCount = Converter.asUnsigned(stream.readShort());
        exceptions = new ArrayList<TableException>();

        for (int i = 0; i < exceptionCount; i++) {
            exception = new TableException();
            exception.read(stream);
            exceptions.add(exception);
        }

        attributeCount = Converter.asUnsigned(stream.readShort());
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
            System.out.println("exp");
        }

        System.out.println("ATW: " + (getLength() - 6));


        stream.writeShort(attributes.size());
        for (Attribute attribute : attributes) {
            attribute.write(constantPool, stream);
            System.out.println("att");
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
}
