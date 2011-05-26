package org.wooddog.woodstub.core.instrumentation;

import org.wooddog.woodstub.core.Converter;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 25-04-11
 * Time: 16:45
 * To change this template use File | Settings | File Templates.
 */
public class AttributeUnknown implements Attribute {
    private int poolIndex;
    private byte[] content;
    private String name;


    public void setConstantPoolIndex(int index) {
        this.poolIndex = index;
    }

    public int getLength() {
        return content.length + 6;
    }

    public void read(ConstantPool constantPool, DataInputStream stream) throws IOException {
        int length;

        name = constantPool.getUtf8(poolIndex).getValue();
        System.out.println("UMK: " + name);

        length = stream.readInt();
        content = new byte[length];

        for (int i = 0; i < length; i++) {
            content[i] = stream.readByte();
        }
    }

    public void write(ConstantPool constantPool, DataOutputStream stream) throws IOException {
        stream.writeShort(poolIndex);
        stream.writeInt(content.length);
        stream.write(content);
    }

    @Override
    public String toString() {
        return "AttributeUnknown{" +
                "poolIndex=" + poolIndex +
                ", content=" + new String(content) +
                '}';
    }

    public String getName() {
        return name;
    }
}
