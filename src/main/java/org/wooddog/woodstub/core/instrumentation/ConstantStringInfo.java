package org.wooddog.woodstub.core.instrumentation;

import org.wooddog.woodstub.core.Converter;
import org.wooddog.woodstub.core.InternalErrorException;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 25-04-11
 * Time: 14:36
 * To change this template use File | Settings | File Templates.
 */
public class ConstantStringInfo implements ConstantPoolInfo {
    private byte tag = 8;
    private int stringIndex;

    public byte getTag() {
        return tag;
    }

    public void setTag(byte tag) {
        this.tag = tag;
    }

    public int getStringIndex() {
        return stringIndex;
    }

    public void setStringIndex(short stringIndex) {
        this.stringIndex = stringIndex;
    }

    public void read(DataInputStream stream) throws IOException {
        stringIndex = stream.readUnsignedShort();
    }

    public void write(DataOutputStream stream) throws IOException {
        stream.writeByte(tag);
        stream.writeShort(stringIndex);
    }

    @Override
    public String toString() {
        return "ConstantStringInfo{" +
                "tag=" + tag +
                ", stringIndex=" + stringIndex +
                '}';
    }
}
