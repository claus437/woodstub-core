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
 * Time: 14:40
 * To change this template use File | Settings | File Templates.
 */
public class ConstantNameAndTypeInfo implements ConstantPoolInfo {
    private byte tag = 12;
    private int nameIndex;
    private int descriptorIndex;

    public byte getTag() {
        return tag;
    }

    public int getNameIndex() {
        return nameIndex;
    }

    public int getDescriptorIndex() {
        return descriptorIndex;
    }

    public void read(DataInputStream stream) throws IOException {
        nameIndex = Converter.asUnsigned(stream.readShort());
        descriptorIndex = Converter.asUnsigned(stream.readShort());
    }

    public void write(DataOutputStream stream) throws IOException {
        stream.writeByte(tag);
        stream.writeShort(nameIndex);
        stream.writeShort(descriptorIndex);
    }

    @Override
    public String toString() {
        return "ConstantNameAndTypeInfo{" +
                "tag=" + tag +
                ", nameIndex=" + nameIndex +
                ", descriptorIndex=" + descriptorIndex +
                '}';
    }
}
