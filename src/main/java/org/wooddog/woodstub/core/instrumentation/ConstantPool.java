package org.wooddog.woodstub.core.instrumentation;

import org.wooddog.woodstub.core.Converter;
import org.wooddog.woodstub.core.InternalErrorException;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 25-04-11
 * Time: 14:25
 * To change this template use File | Settings | File Templates.
 */
public class ConstantPool {
    private static final Map<Integer, Class> POOLINFOTYPES = new HashMap<Integer, Class>();
    private List<ConstantPoolInfo> pool;
    private DataInputStream stream;

    static {
        POOLINFOTYPES.put(1, ConstantUtf8Info.class);
        POOLINFOTYPES.put(3, ConstantIntegerInfo.class);
        POOLINFOTYPES.put(4, ConstantFloatInfo.class);
        POOLINFOTYPES.put(5, ConstantLongInfo.class);
        POOLINFOTYPES.put(6, ConstantDoubleInfo.class);
        POOLINFOTYPES.put(7, ConstantClassInfo.class);
        POOLINFOTYPES.put(8, ConstantStringInfo.class);
        POOLINFOTYPES.put(9, ConstantFieldRefInfo.class);
        POOLINFOTYPES.put(10, ConstantMethodRefInfo.class);
        POOLINFOTYPES.put(11, ConstantInterfaceMethodRefInfo.class);
        POOLINFOTYPES.put(12, ConstantNameAndTypeInfo.class);
    }

    public ConstantPool() {
        pool = new ArrayList<ConstantPoolInfo>();
    }

    public ConstantUtf8Info getUtf8(int index) {
        return (ConstantUtf8Info) pool.get(index -1);
    }

    public void read(DataInputStream stream) throws IOException {
        ConstantPoolInfo entry;
        int length;
        int tag;

        this.stream = stream;
        length = stream.readUnsignedShort();
        System.out.println("length " + length);

        for (int i = 0; i < length - 1; i++) {
            tag = stream.readUnsignedByte();

            entry = createEntry(tag);
            entry.read(stream);

            pool.add(entry);
        }
    }

    public void write(DataOutputStream stream) throws IOException {
        stream.writeShort(pool.size() + 1);

        for (ConstantPoolInfo entry : pool) {
            entry.write(stream);
        }
    }

    public void dump() {
        for (int i = 0; i < pool.size(); i++) {
            System.out.println("#" + (i + 1) + " " + pool.get(i).toString());
        }
    }

    private ConstantPoolInfo createEntry(int tag) {
        Class clazz;
        ConstantPoolInfo entry;

        clazz = POOLINFOTYPES.get(tag);
        if (clazz == null) {

            dump();
            try {
                System.out.println(stream.readUnsignedByte());
            } catch (IOException x) {
                x.printStackTrace();
            }
            throw new InternalErrorException("unknown constant pool entry " + tag);
        }

        try {
            entry = (ConstantPoolInfo) clazz.newInstance();
        } catch (InstantiationException x) {
            throw new InternalErrorException("failed creating constant pool entry for tag " + tag);
        } catch (IllegalAccessException x) {
            throw new InternalErrorException("failed creating constant pool entry for tag " + tag);
        }

        return entry;
    }


}
