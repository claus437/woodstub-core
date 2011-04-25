package org.wooddog.woodstub.core.instrumentation;

import org.wooddog.woodstub.core.InternalErrorException;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 25-04-11
 * Time: 14:41
 * To change this template use File | Settings | File Templates.
 */
public class ConstantUtf8Info implements ConstantPoolInfo {
    private byte tag = 1;
    private String value;

    public byte getTag() {
        return tag;
    }

    public void setTag(byte tag) {
        this.tag = tag;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void read(DataInputStream stream) throws IOException {
        value = stream.readUTF();
    }

    public void write(DataOutputStream stream) throws IOException {
        stream.writeByte(tag);
        stream.writeUTF(value);
    }

    @Override
    public String toString() {
        return "ConstantUtf8Info{" +
                "tag=" + tag +
                ", value='" + value + '\'' +
                '}';
    }
}
