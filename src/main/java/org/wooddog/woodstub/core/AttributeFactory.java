package org.wooddog.woodstub.core;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 23-04-11
 * Time: 22:57
 * To change this template use File | Settings | File Templates.
 */
public class AttributeFactory {
    private static final Map<String, Class<? extends Attribute>> attributeTypes = new HashMap<String, Class<? extends Attribute>>();

    static {
        attributeTypes.put("Code", CodeAttribute.class);
    }

    public static Attribute read(ConstantPoolEntry[] constants, DataInputStream stream) throws IOException {
        short ref;
        String type;
        Attribute attribute;

        ref = stream.readShort();
        type = constants[ref -1].getValue().toString();

        System.out.println(constants[ref -1].getValue());

        attribute = getAttribute(type);
        attribute.read(constants, ref, stream);

        return attribute;
    }

    private static Attribute getAttribute(String type) {
        Class<? extends Attribute> clazz;
        Attribute attribute;

        clazz = attributeTypes.get(type);
        if (clazz == null) {
            clazz = UnknownAttribute.class;
        }

        try {
            attribute = clazz.newInstance();
        } catch (IllegalAccessException x) {
            throw new InternalErrorException("unable to instantiate " + clazz.getCanonicalName(), x);
        } catch (InstantiationException x) {
            throw new InternalErrorException("unable to instantiate " + clazz.getCanonicalName(), x);
        }

        return attribute;
    }
}
