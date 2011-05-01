package org.wooddog.woodstub.core.instrumentation;

import org.wooddog.woodstub.core.Converter;
import org.wooddog.woodstub.core.InternalErrorException;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 25-04-11
 * Time: 16:34
 * To change this template use File | Settings | File Templates.
 */
public class FieldInfo {
    private List<Attribute> attributes;
    private int accessFlags;
    private int nameIndex;
    private int descriptorIndex;


    public FieldInfo() {
        attributes = new ArrayList<Attribute>();
    }

    public void read(ConstantPool constantPool, DataInputStream stream) throws IOException {
        Attribute attribute;
        int attributeCount;

        accessFlags = stream.readUnsignedShort();
        nameIndex = stream.readUnsignedShort();
        descriptorIndex = stream.readUnsignedShort();

        attributeCount = stream.readUnsignedShort();

        for (int i = 0; i < attributeCount; i++) {
            attribute = AttributeFactory.create(constantPool, stream);
            attributes.add(attribute);
        }
    }

    public void write(ConstantPool constantPool, DataOutputStream stream) throws IOException {
        stream.writeShort(accessFlags);
        stream.writeShort(nameIndex);
        stream.writeShort(descriptorIndex);

        AttributeFactory.write(attributes, constantPool, stream);
    }

    public void dump(ConstantPool constantPool) {
        System.out.println("-------- FIELD DUMP -----------");
        System.out.println("accessFlage: " + accessFlags);
        System.out.println("nameIndex: " + nameIndex + " (" + constantPool.getUtf8(nameIndex) + ")");
        System.out.println("descriptorIndex: " + descriptorIndex + " (" + constantPool.getUtf8(descriptorIndex) + ")");

        for (Attribute attribute : attributes) {
            System.out.println(attribute.toString());
        }
        System.out.println("-------------------------------\n");
    }

    public List<Attribute> getAttributes(String name) {
        List<Attribute> attributes;

        attributes = new ArrayList<Attribute>();

        for (Attribute attribute : this.attributes) {
            if (name.equals(attribute.getName())) {
                attributes.add(attribute);
            }
        }

        return attributes;
    }
}
