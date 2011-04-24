package org.wooddog.woodstub.core;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.Arrays;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 23-04-11
 * Time: 19:01
 * To change this template use File | Settings | File Templates.
 */
public class FieldEntry {
    private short accessFlags;
    private short indexOfName;
    private short indexOfDescriptor;
    private Attribute[] attributes;

    public static FieldEntry read(ConstantPoolEntry[] constants, DataInputStream stream) throws IOException {
        FieldEntry entry;
        short size;

        entry = new FieldEntry();
        entry.accessFlags = stream.readShort();
        entry.indexOfName = stream.readShort();
        entry.indexOfDescriptor = stream.readShort();

        size = stream.readShort();
        entry.attributes = new Attribute[size];

        for (int i = 0; i < size; i++) {
            entry.attributes[i] = AttributeFactory.read(constants, stream);
        }

        return entry;
    }

    @Override
    public String toString() {
        return "FieldEntry{" +
                "accessFlags=" + accessFlags +
                ", indexOfName=" + indexOfName +
                ", indexOfDescriptor=" + indexOfDescriptor +
                ", attributes=" + (attributes == null ? null : Arrays.asList(attributes)) +
                '}';
    }
}
