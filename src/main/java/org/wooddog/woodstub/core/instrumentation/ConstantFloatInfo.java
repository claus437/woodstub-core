package org.wooddog.woodstub.core.instrumentation;

import org.wooddog.woodstub.core.InternalErrorException;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 25-04-11
 * Time: 14:37
 * To change this template use File | Settings | File Templates.
 */
public class ConstantFloatInfo implements ConstantPoolInfo {
    private static final int TAG = 4;
    private float value;

    public int getTag() {
        return TAG;
    }


    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public void read(DataInputStream stream) throws IOException {
        value = stream.readFloat();
    }

    public void write(DataOutputStream stream) throws IOException {
        stream.writeByte(TAG);
        stream.writeFloat(value);
    }

    @Override
    public String toString() {
        return "ConstantFloatInfo{" +
                "tag=" + TAG +
                ", value=" + value +
                '}';
    }
}
