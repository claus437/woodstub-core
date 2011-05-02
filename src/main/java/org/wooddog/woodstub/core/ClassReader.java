package org.wooddog.woodstub.core;

import org.wooddog.woodstub.core.instrumentation.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 23-04-11
 * Time: 17:00
 * To change this template use File | Settings | File Templates.
 */
public class ClassReader {
    private int magic;
    private int minor;
    private int major;
    private ConstantPool constants;
    private int accessFlags;
    private int indexOfClass;
    private int indexOfSuper;
    private int[] interfaceReferences;
    private List<FieldInfo> fields;
    private List<FieldInfo> methods;
    private List<Attribute> attributes;

    public void read(InputStream clazz) throws IOException {
        DataInputStream stream;

        stream = new DataInputStream(clazz);

        magic = stream.readInt();
        minor = stream.readUnsignedShort();
        major = stream.readUnsignedShort();

        constants = new ConstantPool();
        constants.read(stream);
        constants.dump();
        accessFlags = stream.readUnsignedShort();
        indexOfClass = stream.readUnsignedShort();
        indexOfSuper = stream.readUnsignedShort();

        interfaceReferences = readInterfaces(stream);
        fields = readFields(constants, stream);
        methods = readFields(constants, stream);
        attributes = AttributeFactory.read(constants, stream);


        int i = 0;
        while(clazz.read() != -1) {
            i++;
        }

        if (i != 0) {
            throw new InternalErrorException("not fully read, remaining bytes " + i);
        }
    }

    public ConstantPool getConstantPool() {
        return constants;
    }

    public int getIndexOfClass() {
        return indexOfClass;
    }

    public void write(OutputStream out) throws IOException {
        DataOutputStream stream;

        stream = new DataOutputStream(out);
        stream.writeInt(magic);
        stream.writeShort(minor);
        stream.writeShort(major);
        constants.write(stream);
        stream.writeShort(accessFlags);
        stream.writeShort(indexOfClass);
        stream.writeShort(indexOfSuper);

        stream.writeShort(interfaceReferences.length);
        for (int reference : interfaceReferences) {
            stream.writeShort(reference);
        }

        stream.writeShort(fields.size());
        for (FieldInfo field : fields) {
            field.write(constants, stream);
        }

        stream.writeShort(methods.size());
        for (FieldInfo method : methods) {
            method.write(constants, stream);
        }

        AttributeFactory.write(attributes, constants, stream);
    }

    public List<? extends Attribute> getAttributes(String name) {
        List<Attribute> attributes;

        attributes = new ArrayList<Attribute>();
        for (FieldInfo method : methods) {
            attributes.addAll(method.getAttributes(name));
        }

        for (FieldInfo field : fields) {
            attributes.addAll(field.getAttributes(name));
        }

        for (Attribute attribute : this.attributes) {
            if (name.equals(attribute.getName())) {
                attributes.add(attribute);
            }
        }

        return attributes;
    }

    public List<FieldInfo> getMethods() {
        return methods;
    }

    private int[] readInterfaces(DataInputStream stream) throws IOException {
        int[] interfaces;
        int size;

        size = stream.readUnsignedShort();
        interfaces = new int[size];

        for (int i = 0; i < size; i++) {
            interfaces[i] = stream.readUnsignedShort();
        }

        return interfaces;
    }

    private List<FieldInfo> readFields(ConstantPool constantPool, DataInputStream stream) throws IOException {
        FieldInfo field;
        List<FieldInfo> fields;
        int fieldCount;

        fieldCount = stream.readUnsignedShort();
        fields = new ArrayList<FieldInfo>();

        for (int i = 0; i < fieldCount; i++) {
            field = new FieldInfo();
            field.read(constantPool, stream);
            fields.add(field);
        }

        return fields;
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
