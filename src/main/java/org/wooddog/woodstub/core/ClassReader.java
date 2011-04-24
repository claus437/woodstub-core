package org.wooddog.woodstub.core;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 23-04-11
 * Time: 17:00
 * To change this template use File | Settings | File Templates.
 */
public class ClassReader {
    private String magic;
    private short minor;
    private short major;
    private ConstantPoolEntry[] constants;
    private short accessFlags;
    private short indexOfClass;
    private short indexOfSuper;
    private short[] interfaceReferences;
    private FieldEntry[] fields;
    private MethodEntry[] methods;
    private Attribute[] attributes;

    public void read(InputStream clazz) throws IOException {
        DataInputStream stream;

        stream = new DataInputStream(clazz);
        magic = Integer.toHexString(stream.readInt());
        minor = stream.readShort();
        major = stream.readShort();

        constants = readConstants(stream);

        accessFlags = stream.readShort();
        indexOfClass = stream.readShort();
        indexOfSuper = stream.readShort();

        interfaceReferences = readInterfaces(stream);
        fields = readFields(stream);

        methods = readMethods(stream);

        attributes = readAttributes(stream);

        int i = 0;
        while(clazz.read() != -1) {
            i++;
        }

        System.out.println("remaing bytes " + i);
    }

    private ConstantPoolEntry[] readConstants(DataInputStream stream) throws IOException {
        ConstantPoolEntry[] pool;
        int size;

        size = stream.readShort();
        pool = new ConstantPoolEntry[size];

        for (int i = 0; i < pool.length - 1; i++) {
            pool[i] = ConstantPoolEntry.read(stream);
        }

        return pool;
    }

    private short[] readInterfaces(DataInputStream stream) throws IOException {
        short[] interfaces;
        int size;

        size = stream.readShort();
        interfaces = new short[size];

        for (int i = 0; i < size; i++) {
            interfaces[i] = stream.readShort();
        }

        return interfaces;
    }

    private FieldEntry[] readFields(DataInputStream stream) throws IOException {
        FieldEntry[] entries;
        int size;

        size = stream.readShort();
        entries = new FieldEntry[size];

        for (int i = 0; i < size; i++) {
            entries[i] = FieldEntry.read(constants, stream);
        }

        return entries;
    }

    private MethodEntry[] readMethods(DataInputStream stream) throws IOException {
        MethodEntry[] entries;
        int size;

        size = stream.readShort();
        entries = new MethodEntry[size];

        for (int i = 0; i < size; i++) {
            entries[i] = MethodEntry.read(constants, stream);
        }

        return entries;
    }

    private Attribute[] readAttributes(DataInputStream stream) throws IOException {
        Attribute[] attributes;
        int size;

        size = stream.readShort();
        attributes = new Attribute[size];

        for (int i = 0; i < size; i++) {
            attributes[i] = AttributeFactory.read(constants, stream);
        }

        return attributes;
    }

    @Override
    public String toString() {
        return "ClassReader{" +
                "magic='" + magic + '\'' +
                ", minor=" + minor +
                ", major=" + major +
                ", constants=" + (constants == null ? null : Arrays.asList(constants)) +
                ", accessFlags=" + accessFlags +
                ", indexOfClass=" + indexOfClass +
                ", indexOfSuper=" + indexOfSuper +
                ", interfaceReferences=" + interfaceReferences +
                ", fields=" + (fields == null ? null : Arrays.asList(fields)) +
                ", methods=" + (methods == null ? null : Arrays.asList(methods)) +
                '}';
    }
}
