package org.wooddog.woodstub.core.instrumentation;

import org.wooddog.woodstub.core.Converter;
import org.wooddog.woodstub.core.InternalErrorException;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 25-04-11
 * Time: 18:44
 * To change this template use File | Settings | File Templates.
 */
public class AttributeFactory {
    private static final Class ATTRIBUTE_UNKNOWN = AttributeUnknown.class;
    private static final Map<String, Class> ATTRIBUTE_TYPES = new HashMap<String, Class>();

    static {
        ATTRIBUTE_TYPES.put("Code", AttributeCode.class);
    }

    public static Attribute create(ConstantPool constantPool, DataInputStream stream) throws  IOException {
        Attribute attribute;
        int poolIndex;
        String type;

        poolIndex = stream.readUnsignedShort();
        type = constantPool.getUtf8(poolIndex).getValue();

        attribute = createAttribute(type);
        attribute.setConstantPoolIndex(poolIndex);
        attribute.read(constantPool, stream);

        return attribute;
    }

    public static List<Attribute> read(ConstantPool constantPool, DataInputStream stream) throws IOException {
        Attribute attribute;
        List<Attribute> attributes;
        int attributeCount;

        attributeCount = stream.readUnsignedShort();
        attributes = new ArrayList<Attribute>();

        for (int i = 0; i < attributeCount; i++) {
            attribute = create(constantPool, stream);
            attributes.add(attribute);
        }

        return attributes;
    }

    public static void write(List<Attribute> attributes, ConstantPool constantPool, DataOutputStream stream) throws IOException {
        stream.writeShort(attributes.size());

        for (Attribute attribute : attributes) {
            attribute.write(constantPool, stream);
        }
    }

    private static Attribute createAttribute(String type) throws IOException {
        Class clazz;
        Attribute attribute;

        clazz = ATTRIBUTE_TYPES.get(type);
        if (clazz == null) {
            clazz = ATTRIBUTE_UNKNOWN;
        }

        try {
            attribute = (Attribute) clazz.newInstance();
        } catch (IllegalAccessException x) {
            throw new InternalErrorException("failed createing instance of attribute " + clazz.getSimpleName());
        } catch (InstantiationException x) {
            throw new InternalErrorException("failed createing instance of attribute " + clazz.getSimpleName());
        }

        return attribute;
    }

}
