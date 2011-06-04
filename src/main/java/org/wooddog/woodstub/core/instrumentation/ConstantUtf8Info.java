package org.wooddog.woodstub.core.instrumentation;

import com.sun.org.apache.bcel.internal.classfile.ConstantUtf8;
import org.wooddog.woodstub.core.InternalErrorException;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 25-04-11
 * Time: 14:41
 * To change this template use File | Settings | File Templates.
 */
public class ConstantUtf8Info implements ConstantPoolInfo, ConstantPoolValue {
    private static final int TAG = 1;
    private String value;

    public ConstantUtf8Info() {
    }

    public ConstantUtf8Info(String value) {
        this.value = value;
    }

    public int getTag() {
        return TAG;
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
        stream.writeByte(TAG);
        stream.writeUTF(value);
    }

    public static int indexOf(List<ConstantPoolInfo> pool, String value) {
        ConstantPoolInfo info;

        for (int i = 0; i < pool.size(); i++) {
            info = pool.get(i);

            if (info == null) {
                continue;
            }

            if (info.getTag() == TAG && ((ConstantUtf8Info) info).getValue().equals(value)) {
                return i;
            }
        }

        return -1;
    }


    @Override
    public String toString() {
        return "ConstantUtf8Info{" +
                "tag=" + TAG +
                ", value='" + value + '\'' +
                '}';
    }

    public String[] values() {
        return new String[]{"UTF8", value};
    }
}
