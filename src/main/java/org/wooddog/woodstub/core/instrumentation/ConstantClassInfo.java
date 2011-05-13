package org.wooddog.woodstub.core.instrumentation;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 25-04-11
 * Time: 14:30
 * To change this template use File | Settings | File Templates.
 */
public class ConstantClassInfo implements ConstantPoolInfo {
    private static final int TAG = 7;
    private int nameIndex;

    public ConstantClassInfo() {
    }

    public ConstantClassInfo(int nameIndex) {
        this.nameIndex = nameIndex;
    }

    public int getTag() {
        return TAG;
    }

    public int getNameIndex() {
        return nameIndex;
    }

    public void setNameIndex(int nameIndex) {
        this.nameIndex = nameIndex;
    }

    public void read(DataInputStream stream) throws IOException {
        nameIndex = stream.readUnsignedShort();
    }

    public void write(DataOutputStream stream) throws IOException {
        stream.writeByte(TAG);
        stream.writeShort(nameIndex);
    }

    public static int indexOf(List<ConstantPoolInfo> constants, int nameIndex) {
        for (int i = 0; i < constants.size(); i++) {
            if (constants.get(i) == null) {
                continue;
            }

            if (constants.get(i) instanceof ConstantClassInfo) {
                if (nameIndex == ((ConstantClassInfo) constants.get(i)).getNameIndex()) {
                    return i;
                }
            }
        }

        return -1;
    }

    @Override
    public String toString() {
        return "ConstantClassInfo{" +
                "tag=" + TAG +
                ", nameIndex=" + nameIndex +
                '}';
    }
}
