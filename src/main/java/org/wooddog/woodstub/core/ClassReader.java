package org.wooddog.woodstub.core;

import org.wooddog.woodstub.core.instrumentation.Attribute;
import org.wooddog.woodstub.core.instrumentation.AttributeFactory;
import org.wooddog.woodstub.core.instrumentation.ConstantPool;
import org.wooddog.woodstub.core.instrumentation.FieldInfo;

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
    private short accessFlags;
    private short indexOfClass;
    private short indexOfSuper;
    private int[] interfaceReferences;
    private List<FieldInfo> fields;
    private List<FieldInfo> methods;
    private List<Attribute> attributes;

    public void read(InputStream clazz) throws IOException {
        DataInputStream stream;

        stream = new DataInputStream(clazz);

        magic = stream.readInt();
        minor = Converter.asUnsigned(stream.readShort());
        major = Converter.asUnsigned(stream.readShort());

        constants = new ConstantPool();
        constants.read(stream);
        constants.dump();

        accessFlags = stream.readShort();
        indexOfClass = stream.readShort();
        indexOfSuper = stream.readShort();

        interfaceReferences = readInterfaces(stream);
        fields = readFields(constants, stream);
        methods = readFields(constants, stream);
        attributes = AttributeFactory.read(constants, stream);

        int i = 0;
        while(clazz.read() != -1) {
            i++;
        }

        System.out.println("remaing bytes " + i);
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


    private int[] readInterfaces(DataInputStream stream) throws IOException {
        int[] interfaces;
        int size;

        size = Converter.asUnsigned(stream.readShort());
        interfaces = new int[size];

        for (int i = 0; i < size; i++) {
            interfaces[i] = stream.readShort();
        }

        return interfaces;
    }

    private List<FieldInfo> readFields(ConstantPool constantPool, DataInputStream stream) throws IOException {
        FieldInfo field;
        List<FieldInfo> fields;
        int fieldCount;

        fieldCount = Converter.asUnsigned(stream.readShort());
        fields = new ArrayList<FieldInfo>();

        for (int i = 0; i < fieldCount; i++) {
            field = new FieldInfo();
            field.read(constantPool, stream);
            fields.add(field);
            field.dump(constantPool);
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
