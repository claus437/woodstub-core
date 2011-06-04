package org.wooddog.woodstub.core.instrumentation;

import org.wooddog.woodstub.core.InternalErrorException;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 25-04-11
 * Time: 14:39
 * To change this template use File | Settings | File Templates.
 */
public class ConstantLongInfo implements ConstantPoolInfo, ConstantPoolValue {
    private static final int TAG = 5;
    private long value;

    public int getTag() {
        return TAG;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public void read(DataInputStream stream) throws IOException {
        value = stream.readLong();
    }

    public void write(DataOutputStream stream) throws IOException {
        stream.writeByte(TAG);
        stream.writeLong(value);
    }

    @Override
    public String toString() {
        return "ConstantLongInfo{" +
                "tag=" + TAG +
                ", value=" + value +
                '}';
    }

    public String[] values() {
        return new String[]{"LONG", Long.toString(value)};
    }

}
