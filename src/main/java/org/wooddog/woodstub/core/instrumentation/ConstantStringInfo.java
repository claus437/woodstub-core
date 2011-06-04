package org.wooddog.woodstub.core.instrumentation;

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
 * Time: 14:36
 * To change this template use File | Settings | File Templates.
 */
public class ConstantStringInfo implements ConstantPoolInfo {
    private static final int TAG = 8;
    private int stringIndex;

    public ConstantStringInfo() {
    }

    public ConstantStringInfo(int stringIndex) {
        this.stringIndex = stringIndex;
    }

    public int getTag() {
        return TAG;
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
        stream.writeByte(TAG);
        stream.writeShort(stringIndex);
    }

    public static int indexOf(List<ConstantPoolInfo> pool, int stringIndex) {
        ConstantPoolInfo info;

        for (int i = 0; i < pool.size(); i++) {
            info = pool.get(i);

            if (info == null) {
                continue;
            }

            if (info.getTag() == TAG && ((ConstantStringInfo) info).getStringIndex() == stringIndex) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public String toString() {
        return "ConstantStringInfo{" +
                "tag=" + TAG +
                ", stringIndex=" + stringIndex +
                '}';
    }

    public String[] values() {
        return new String[]{"STRING", Integer.toString(stringIndex)};
    }
}
