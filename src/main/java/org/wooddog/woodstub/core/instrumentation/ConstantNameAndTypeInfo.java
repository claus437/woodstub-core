package org.wooddog.woodstub.core.instrumentation;

import org.omg.IOP.TAG_ORB_TYPE;
import org.wooddog.woodstub.core.Converter;
import org.wooddog.woodstub.core.InternalErrorException;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 25-04-11
 * Time: 14:40
 * To change this template use File | Settings | File Templates.
 */
public class ConstantNameAndTypeInfo implements ConstantPoolInfo {
    private static final int TAG = 12;
    private int nameIndex;
    private int descriptorIndex;

    public ConstantNameAndTypeInfo() {
    }

    public ConstantNameAndTypeInfo(int nameIndex, int descriptorIndex) {
        this.nameIndex = nameIndex;
        this.descriptorIndex = descriptorIndex;
    }

    public int getTag() {
        return TAG;
    }

    public int getNameIndex() {
        return nameIndex;
    }

    public int getDescriptorIndex() {
        return descriptorIndex;
    }

    public void setNameIndex(int nameIndex) {
        this.nameIndex = nameIndex;
    }

    public void setDescriptorIndex(int descriptorIndex) {
        this.descriptorIndex = descriptorIndex;
    }

    public void read(DataInputStream stream) throws IOException {
        nameIndex = stream.readUnsignedShort();
        descriptorIndex = stream.readUnsignedShort();
    }

    public void write(DataOutputStream stream) throws IOException {
        stream.writeByte(TAG);
        stream.writeShort(nameIndex);
        stream.writeShort(descriptorIndex);
    }


    public static int indexOf(List<ConstantPoolInfo> constants, int nameIndex, int descriptorIndex) {
        ConstantNameAndTypeInfo info;

        for (int i = 0; i < constants.size(); i++) {
            if (constants.get(i) == null) {
                continue;
            }

            if (constants.get(i).getTag() == TAG) {
                info = (ConstantNameAndTypeInfo) constants.get(i);

                if (nameIndex == info.getNameIndex() && descriptorIndex == info.getDescriptorIndex()) {
                    return i;
                }
            }
        }

        return -1;
    }

    @Override
    public String toString() {
        return "ConstantNameAndTypeInfo{" +
                "tag=" + TAG +
                ", nameIndex=" + nameIndex +
                ", descriptorIndex=" + descriptorIndex +
                '}';
    }
}
