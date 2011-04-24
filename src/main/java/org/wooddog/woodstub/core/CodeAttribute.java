package org.wooddog.woodstub.core;

import java.io.DataInputStream;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 23-04-11
 * Time: 23:13
 * To change this template use File | Settings | File Templates.
 */
public class CodeAttribute implements Attribute {
    private short indexOfName;
    private int attributeLength;
    private short maxStack;
    private short maxLocals;
    private int codeLength;
    private byte[] code;
    private ExceptionEntry[] exceptions;
    private Attribute[] attributes;

    @Override
    public String toString() {
        return "Code{" +
                "indexOfName=" + indexOfName +
                ", attributeLength=" + attributeLength +
                ", maxStack=" + maxStack +
                ", maxLocals=" + maxLocals +
                ", codeLength=" + codeLength +
                ", code=" + code +
                '}';
    }

    public void read(ConstantPoolEntry[] constants, short index, DataInputStream stream) throws IOException {
        short exceptionCount;
        short attributeCount;

        indexOfName = index;
        attributeLength = stream.readInt();
        maxStack = stream.readShort();
        maxLocals = stream.readShort();
        codeLength = stream.readInt();

        code = new byte[codeLength];

        Code.read(stream, codeLength);

        exceptionCount = stream.readShort();
        exceptions = new ExceptionEntry[exceptionCount];

        for (int i = 0; i < exceptionCount; i++) {
            exceptions[i] = ExceptionEntry.read(stream);
        }

        attributeCount = stream.readShort();
        attributes = new Attribute[attributeCount];

        for (int i = 0; i < attributeCount; i++) {
            attributes[i] = AttributeFactory.read(constants, stream);
        }
    }

}
