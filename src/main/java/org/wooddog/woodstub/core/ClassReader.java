package org.wooddog.woodstub.core;

import org.wooddog.woodstub.core.formatter.CodeFormatter;
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

    public List<FieldInfo> getFields() {
        return fields;
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

    public String getSource() throws IOException {
        StringBuffer buffer;
        List<ConstantPoolInfo> constantEntries;
        CodeFormatter codeFormatter;
        String lineNum;
        int maxWidth;
        String[] values;

        codeFormatter = new CodeFormatter(constants);
        buffer = new StringBuffer();
        buffer.append(Integer.toHexString(magic) + " version " + major + "." + minor + "\n\n");

        constantEntries = constants.getConstants();
        maxWidth = Integer.toString(constantEntries.size()).length();

        for (int i = 0; i < constantEntries.size(); i++) {
            if (constantEntries.get(i) != null) {
                values = constantEntries.get(i).values();

                lineNum = Integer.toString(i);
                fill(buffer, maxWidth - lineNum.length(), ' ');
                buffer.append(lineNum);
                buffer.append(" ");
                buffer.append(values[0]);
                fill(buffer, 16 - values[0].length(), ' ');
                buffer.append(" ");
                for (int j = 1; j < values.length; j++) {
                    buffer.append(values[j]);
                    if (j < values.length - 1) {
                        buffer.append(", ");
                    }
                }
                buffer.append("\n");
            }
        }

        buffer.append("\n");

        buffer.append("ACCESS FLAGS:   " + Integer.toString(accessFlags) + "\n");
        buffer.append("INDEX OF CLASS: " + Integer.toString(indexOfClass) + "\n");
        buffer.append("INDEX OF SUPER: " + Integer.toString(indexOfSuper) + "\n");
        buffer.append("IMPLEMENTS:     ");
        for (int i = 0; i < interfaceReferences.length; i++) {
            buffer.append(interfaceReferences[i]);
            if (i < interfaceReferences.length - 1) {
                buffer.append(", ");
            }
        }
        buffer.append("\n\n");
        buffer.append("FIELDS:\n");
        for (int i = 0; i < fields.size(); i ++) {
            buffer.append("NAME: " + fields.get(i).getNameIndex() + " DESCRIPTOR: " + fields.get(i).getDescriptorIndex() + " FLAGS: " + fields.get(i).getAccessFlags() + "\n");
        }

        buffer.append("\n");
        for (FieldInfo method : methods) {
            buffer.append("METHOD: " + ((ConstantUtf8Info) constants.get(method.getNameIndex())).getValue());
            buffer.append(((ConstantUtf8Info) constants.get(method.getDescriptorIndex())).getValue());
            buffer.append(", FLAGS: " + method.getAccessFlags());
            buffer.append(", ");
            List<Attribute> attributes = method.getAttributes();
            for (int i = 0; i < attributes.size(); i++) {
                if (attributes.get(i) instanceof AttributeCode) {
                    codeFormatter.write((AttributeCode) attributes.get(i), buffer);
                } else {
                    buffer.append("UNKNWON ATT: " + attributes.get(i).getName());
                }
            }
        }

        return buffer.toString();
    }

    private void fill(StringBuffer buffer, int length, char value) {
        for (int i = 0; i < length; i ++) {
            buffer.append(value);
        }
    }
}
