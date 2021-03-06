package org.wooddog.woodstub.core.instrumentation;

import com.sun.org.apache.bcel.internal.classfile.ConstantNameAndType;
import org.wooddog.woodstub.core.InternalErrorException;
import org.wooddog.woodstub.core.MyLogger;

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
        pool.add(null);
    }

    public List<ConstantPoolInfo> getConstants() {
        return pool;
    }

    public ConstantPoolInfo get(int index) {
        return pool.get(index);
    }

    public ConstantUtf8Info getUtf8(int index) {
        return (ConstantUtf8Info) pool.get(index);
    }

    public void read(DataInputStream stream) throws IOException {
        ConstantPoolInfo entry;
        int length;
        int tag;

        this.stream = stream;
        length = stream.readUnsignedShort();


        for (int i = 0; i < length - 1; i++) {
            tag = stream.readUnsignedByte();

            entry = createEntry(tag);
            entry.read(stream);

            pool.add(entry);

            if (entry instanceof ConstantLongInfo || entry instanceof ConstantDoubleInfo) {
                pool.add(null);
                i++;
            }
        }
    }

    public void write(DataOutputStream stream) throws IOException {
        stream.writeShort(pool.size());

        for (ConstantPoolInfo entry : pool) {
            if (entry != null) {
                entry.write(stream);
            }
        }
    }

    public void dump() {
        for (int i = 0; i < pool.size(); i++) {
            if (pool.get(i) == null) {
                continue;
            }

            MyLogger.write("#" + i + " " + pool.get(i).toString() + "\n");
        }
    }

    public int size() {
        return pool.size();
    }

    private ConstantPoolInfo createEntry(int tag) {
        Class clazz;
        ConstantPoolInfo entry;

        clazz = POOLINFOTYPES.get(tag);
        if (clazz == null) {

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

    public int add(ConstantPoolInfo entry) {
        pool.add(entry);
        return pool.size() - 1;
    }


    // InterfaceMethodRefInfo -> ClassInfo -> Utf8Info
    //                        |
    //                        -> NameAndTypeInfo -> Utf8Info
    //                                           |
    //                                           -> Utf8Info
    public int addInterfaceMethodRefInfo(String className, String methodName, String descriptor) {
        int idxU8ClassName;
        int idxClassInfo;
        int idxU8MethodName;
        int idxU8Descriptor;
        int idxNameAndType;
        int idxMethodRefInfo;

        // CLASS
        idxU8ClassName = ConstantUtf8Info.indexOf(pool, className);
        if (idxU8ClassName == -1) {
            idxU8ClassName = add(new ConstantUtf8Info(className));
        }

        idxClassInfo = ConstantClassInfo.indexOf(pool, idxU8ClassName);
        if (idxClassInfo == -1) {
            idxClassInfo = add(new ConstantClassInfo(idxU8ClassName));
        }


        // METHOD & TYPE
        idxU8MethodName = ConstantUtf8Info.indexOf(pool, methodName);
        if (idxU8MethodName == -1) {
            idxU8MethodName = add(new ConstantUtf8Info(methodName));
        }

        idxU8Descriptor = ConstantUtf8Info.indexOf(pool, descriptor);
        if (idxU8Descriptor == -1) {
            idxU8Descriptor = add(new ConstantUtf8Info(descriptor));
        }

        idxNameAndType = ConstantNameAndTypeInfo.indexOf(pool, idxU8MethodName, idxU8Descriptor);
        if (idxNameAndType == -1) {
            idxNameAndType = add(new ConstantNameAndTypeInfo(idxU8MethodName, idxU8Descriptor));
        }


        // ROOT
        idxMethodRefInfo = ConstantInterfaceMethodRefInfo.indexOf(pool, idxClassInfo, idxNameAndType);
        if (idxMethodRefInfo == -1) {
            idxMethodRefInfo = add(new ConstantInterfaceMethodRefInfo(idxClassInfo, idxNameAndType));
        }

        return idxMethodRefInfo;
    }


    // MethodRefInfo -> ClassInfo -> Utf8Info
    //               |
    //               -> NameAndTypeInfo -> Utf8Info
    //                                  |
    //                                  -> Utf8Info
    //
    //
    //

    public int addMethodRef(String className, String methodName, String descriptor) {
        int idxU8ClassName;
        int idxClassInfo;
        int idxU8MethodName;
        int idxU8Descriptor;
        int idxNameAndType;
        int idxMethodRefInfo;

        // CLASS
        idxU8ClassName = ConstantUtf8Info.indexOf(pool, className);
        if (idxU8ClassName == -1) {
            idxU8ClassName = add(new ConstantUtf8Info(className));
        }

        idxClassInfo = ConstantClassInfo.indexOf(pool, idxU8ClassName);
        if (idxClassInfo == -1) {
            idxClassInfo = add(new ConstantClassInfo(idxU8ClassName));
        }


        // METHOD & TYPE
        idxU8MethodName = ConstantUtf8Info.indexOf(pool, methodName);
        if (idxU8MethodName == -1) {
            idxU8MethodName = add(new ConstantUtf8Info(methodName));
        }

        idxU8Descriptor = ConstantUtf8Info.indexOf(pool, descriptor);
        if (idxU8Descriptor == -1) {
            idxU8Descriptor = add(new ConstantUtf8Info(descriptor));
        }

        idxNameAndType = ConstantNameAndTypeInfo.indexOf(pool, idxU8MethodName, idxU8Descriptor);
        if (idxNameAndType == -1) {
            idxNameAndType = add(new ConstantNameAndTypeInfo(idxU8MethodName, idxU8Descriptor));
        }


        // ROOT
        idxMethodRefInfo = ConstantMethodRefInfo.indexOf(pool, idxClassInfo, idxNameAndType);
        if (idxMethodRefInfo == -1) {
            idxMethodRefInfo = add(new ConstantMethodRefInfo(idxClassInfo, idxNameAndType));
        }

        return idxMethodRefInfo;
    }

    public int addClass(String name) {
        int idxUtf8;
        int idxClassInfo;

        idxUtf8 = ConstantUtf8Info.indexOf(pool, name);
        if (idxUtf8 == -1) {
            idxUtf8 = add(new ConstantUtf8Info(name));
        }

        idxClassInfo = ConstantClassInfo.indexOf(pool, idxUtf8);
        if (idxClassInfo == -1) {
            idxClassInfo = add(new ConstantClassInfo(idxUtf8));
        }

        return idxClassInfo;

    }

    public int addUtf8Info(String value) {
        int idxUtf8;

        idxUtf8 = ConstantUtf8Info.indexOf(pool, value);
        if (idxUtf8 == -1) {
            idxUtf8 = add(new ConstantUtf8Info(value));
        }

        return idxUtf8;
    }

    public int addNameAndType(String name, String type) {
        int nameIndex;
        int descriptorIndex;
        int index;

        nameIndex = addUtf8Info(name);
        descriptorIndex = addUtf8Info(type);

        index = ConstantNameAndTypeInfo.indexOf(pool, nameIndex, descriptorIndex);
        if (index == -1) {
            index = add(new ConstantNameAndTypeInfo(nameIndex, descriptorIndex));
        }

        return index;
    }

    public int addString(String value) {
        int utf8Index;
        int index;

        utf8Index = addUtf8Info(value);
        index = ConstantStringInfo.indexOf(pool, utf8Index);
        if (index == -1) {
            index = add(new ConstantStringInfo(utf8Index));
        }

        return index;
    }

    public String getClassName(int classIndex) {
        ConstantClassInfo classInfo;
        ConstantUtf8Info utf8Info;

        classInfo = (ConstantClassInfo) pool.get(classIndex);
        utf8Info = (ConstantUtf8Info) pool.get(classInfo.getNameIndex());

        return utf8Info.getValue();
    }

    public String getMethodName(int nameAndTypeIndex) {
        ConstantNameAndTypeInfo nameAndTypeInfo;
        ConstantUtf8Info utf8Info;

        nameAndTypeInfo = (ConstantNameAndTypeInfo) pool.get(nameAndTypeIndex);
        utf8Info = (ConstantUtf8Info) pool.get(nameAndTypeInfo.getNameIndex());

        return utf8Info.getValue();
    }

    public String getSignature(int nameAndTypeIndex) {
        ConstantNameAndTypeInfo nameAndTypeInfo;
        ConstantUtf8Info utf8Info;

        nameAndTypeInfo = (ConstantNameAndTypeInfo) pool.get(nameAndTypeIndex);
        utf8Info = (ConstantUtf8Info) pool.get(nameAndTypeInfo.getDescriptorIndex());

        return utf8Info.getValue();
    }

    public String getUtf8Value(int index) {
        return ((ConstantUtf8Info) pool.get(index)).getValue();
    }
}
