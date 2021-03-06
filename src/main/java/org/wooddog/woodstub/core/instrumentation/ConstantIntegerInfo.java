package org.wooddog.woodstub.core.instrumentation;

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
public class ConstantIntegerInfo implements ConstantPoolInfo, ConstantPoolValue {
    private static final int TAG = 3;
    private int value;

    public int getTag() {
        return TAG;
    }

    public Object getValue() {
        return value;
    }

    public void read(DataInputStream stream) throws IOException {
        value = stream.readInt();
    }

    public void write(DataOutputStream stream) throws IOException {
        stream.writeByte(TAG);
        stream.writeInt(value);
    }

    @Override
    public String toString() {
        return "ConstantIntegerInfo{" +
                "tag=" + TAG +
                ", value=" + value +
                '}';
    }

    public String[] values() {
        return new String[]{"INTEGER", Integer.toString(value)};
    }
}
