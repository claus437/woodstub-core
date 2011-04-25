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
 * Time: 14:32
 * To change this template use File | Settings | File Templates.
 */
public class ConstantFieldRefInfo implements ConstantPoolInfo {
    private byte tag = 9;
    private int classIndex;
    private int nameAndTypeIndex;

    public byte getTag() {
        return tag;
    }

    public void setTag(byte tag) {
        this.tag = tag;
    }

    public int getClassIndex() {
        return classIndex;
    }

    public void setClassIndex(short classIndex) {
        this.classIndex = classIndex;
    }

    public int getNameAndTypeIndex() {
        return nameAndTypeIndex;
    }

    public void setNameAndTypeIndex(short nameAndTypeIndex) {
        this.nameAndTypeIndex = nameAndTypeIndex;
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
        return "ConstantFieldRefInfo{" +
                "tag=" + tag +
                ", classIndex=" + classIndex +
                ", nameAndTypeIndex=" + nameAndTypeIndex +
                '}';
    }
}
