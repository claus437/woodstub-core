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
 * Time: 14:30
 * To change this template use File | Settings | File Templates.
 */
public class ConstantClassInfo implements ConstantPoolInfo {
    private byte tag = 7;
    private int nameIndex;

    public byte getTag() {
        return tag;
    }

    public void setTag(byte tag) {
        this.tag = tag;
    }

    public int getNameIndex() {
        return nameIndex;
    }

    public void setNameIndex(int nameIndex) {
        this.nameIndex = nameIndex;
    }

    public void read(DataInputStream stream) throws IOException {
        nameIndex = Converter.asUnsigned(stream.readShort());
    }

    public void write(DataOutputStream stream) throws IOException {
        stream.writeByte(tag);
        stream.writeShort(nameIndex);
    }

    @Override
    public String toString() {
        return "ConstantClassInfo{" +
                "tag=" + tag +
                ", nameIndex=" + nameIndex +
                '}';
    }
}
