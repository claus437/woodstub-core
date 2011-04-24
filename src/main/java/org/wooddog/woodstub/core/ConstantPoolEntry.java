package org.wooddog.woodstub.core;

import com.sun.org.apache.bcel.internal.classfile.ConstantPool;

import java.io.DataInputStream;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 23-04-11
 * Time: 17:02
 * To change this template use File | Settings | File Templates.
 */
public class ConstantPoolEntry {
    public static final byte CONSTANT_CLASS = 7;
    public static final byte CONSTANT_FIELD_REF = 9;
    public static final byte CONSTANT_METHOD_REF = 10;
    public static final byte CONSTANT_INTERFACE_METHOD_REF = 11;
    public static final byte CONSTANT_STRING = 8;
    public static final byte CONSTANT_INTEGER = 3;
    public static final byte CONSTANT_FLOAT = 4;
    public static final byte CONSTANT_LONG = 5;
    public static final byte CONSTANT_DOUBLE = 6;
    public static final byte CONSTANT_NAME_AND_TYPE = 12;
    public static final byte CONSTANT_UTF = 1;

    private byte tag;
    private Object value;

    public byte getTag() {
        return tag;
    }

    public Object getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "ConstantPoolEntry{" +
                "tag=" + tag +
                ", value=" + value +
                '}';
    }

    public static ConstantPoolEntry read(DataInputStream stream) throws IOException {
        ConstantPoolEntry entry;

        entry = new ConstantPoolEntry();
        entry.tag = stream.readByte();


        switch (entry.tag) {
            case CONSTANT_CLASS:
                entry.value = stream.readShort();
                break;

            case CONSTANT_FIELD_REF:
                entry.value = stream.readShort() + " " + stream.readShort();
                break;

            case CONSTANT_METHOD_REF:
                entry.value = stream.readShort() + " " + stream.readShort();
                break;

            case CONSTANT_INTERFACE_METHOD_REF:
                entry.value = stream.readShort() + " " + stream.readShort();
                break;

            case CONSTANT_STRING:
                entry.value = stream.readShort();
                break;

            case CONSTANT_INTEGER:
                entry.value = stream.readInt();
                break;

            case CONSTANT_FLOAT:
                entry.value = stream.readFloat();
                break;

            case CONSTANT_LONG:
                entry.value = stream.readLong();
                break;

            case CONSTANT_DOUBLE:
                entry.value = stream.readDouble();
                break;

            case CONSTANT_NAME_AND_TYPE:
                entry.value = stream.readShort() + " " + stream.readShort();
                break;

            case CONSTANT_UTF:
                entry.value = stream.readUTF();
                break;

            default:
                throw new InternalErrorException("unknown tag " + entry.tag);
        }

        return entry;
    }
}
