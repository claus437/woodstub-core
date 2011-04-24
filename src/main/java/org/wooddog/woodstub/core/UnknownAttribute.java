package org.wooddog.woodstub.core;

import java.io.DataInputStream;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 23-04-11
 * Time: 23:03
 * To change this template use File | Settings | File Templates.
 */
public class UnknownAttribute implements Attribute {
    private short indexOfName;
    private byte[] content;

    public void read(ConstantPoolEntry[] constants, short index, DataInputStream stream) throws IOException {
        int size;

        indexOfName = index;

        size = stream.readInt();
        content = new byte[size];

        for (int i = 0; i < size; i++) {
            content[i] = stream.readByte();
        }
    }

    @Override
    public String toString() {
        return "Attribute{" +
                "indexOfIname=" + indexOfName +
                ", content=" + content +
                '}';
    }
}
