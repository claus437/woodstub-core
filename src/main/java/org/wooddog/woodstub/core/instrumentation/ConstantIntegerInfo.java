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
public class ConstantIntegerInfo implements ConstantPoolInfo {
    private byte tag = 3;
    private int value;

    public byte getTag() {
        return tag;
    }

    public void read(DataInputStream stream) throws IOException {
        value = stream.readInt();
    }

    public void write(DataOutputStream stream) throws IOException {
        stream.writeByte(tag);
        stream.writeInt(value);
    }

    @Override
    public String toString() {
        return "ConstantIntegerInfo{" +
                "tag=" + tag +
                ", value=" + value +
                '}';
    }
}
