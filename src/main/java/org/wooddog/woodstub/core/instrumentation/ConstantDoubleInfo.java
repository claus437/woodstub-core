package org.wooddog.woodstub.core.instrumentation;

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
public class ConstantDoubleInfo implements ConstantPoolInfo {
    private static final int TAG = 6;
    private double value;

    public int getTag() {
        return TAG;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public void read(DataInputStream stream) throws IOException {
        value = stream.readDouble();
    }

    public void write(DataOutputStream stream) throws IOException {
        stream.writeByte(TAG);
        stream.writeDouble(value);
    }

    @Override
    public String toString() {
        return "ConstantDoubleInfo{" +
                "TAG=" + TAG +
                ", value=" + value +
                '}';
    }
}
