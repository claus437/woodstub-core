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
 * Time: 14:35
 * To change this template use File | Settings | File Templates.
 */
public class ConstantInterfaceMethodRefInfo implements ConstantPoolInfo {
    private byte tag = 11;
    private int classIndex;
    private int nameAndTypeIndex;


    public byte getTag() {
        return tag;
    }

    public void read(DataInputStream stream) throws IOException {
        classIndex = Converter.asUnsigned(stream.readShort());
        nameAndTypeIndex = Converter.asUnsigned(stream.readShort());
    }

    public void write(DataOutputStream stream) throws IOException {
        stream.writeByte(tag);
        stream.writeShort(classIndex);
        stream.writeShort(nameAndTypeIndex);
    }

    @Override
    public String toString() {
        return "ConstantInterfaceMethodRefInfo{" +
                "tag=" + tag +
                ", classIndex=" + classIndex +
                ", nameAndTypeIndex=" + nameAndTypeIndex +
                '}';
    }
}
