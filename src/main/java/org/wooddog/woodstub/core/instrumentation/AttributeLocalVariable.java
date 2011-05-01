package org.wooddog.woodstub.core.instrumentation;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 01-05-11
 * Time: 13:29
 * To change this template use File | Settings | File Templates.
 */
public class AttributeLocalVariable implements Attribute {
    private int poolIndex;

    public void setConstantPoolIndex(int index) {
        this.poolIndex = index;
    }

    public int getLength() {
        return 0;
    }

    public void read(ConstantPool constantPool, DataInputStream stream) throws IOException {
        int length;
        int tableLength;

        length = stream.readInt();
        tableLength = stream.readUnsignedShort();
    }

    public void write(ConstantPool constantPool, DataOutputStream stream) throws IOException {

    }

    public String getName() {
        return "LocalVariable";
    }
}
