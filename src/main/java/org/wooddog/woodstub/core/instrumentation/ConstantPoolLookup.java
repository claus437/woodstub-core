package org.wooddog.woodstub.core.instrumentation;

import com.sun.org.apache.bcel.internal.classfile.ConstantNameAndType;
import com.sun.org.apache.bcel.internal.classfile.ConstantUtf8;
import com.sun.xml.internal.bind.v2.model.core.ClassInfo;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: DENCBR
 * Date: 02-05-11
 * Time: 17:14
 * To change this template use File | Settings | File Templates.
 */
public class ConstantPoolLookup {
    private ConstantPool constantPool;
    private List<ConstantPoolInfo> constants;

    public ConstantPoolLookup(ConstantPool pool) {
        this.constantPool = constantPool;
        constants = constantPool.getConstants();
    }

    // org/wooddog/woodstub/core/WoodStub getStubFactory ()Lorg/wooddog/woodstub/core/runtime/StubFactory;
    public int getMethodRefInfo(String clazz, String name, String descriptor) {
        List<ConstantPoolInfo> list;
        ConstantPoolInfo info;
        ConstantClassInfo classInfo;
        ConstantNameAndTypeInfo nameAndTypeInfo;
        String value;

        list = constantPool.getConstants();

        for (int i = 0; i < list.size(); i++) {
            info = list.get(i);

            if (info instanceof ConstantMethodRefInfo) {
                classInfo = (ConstantClassInfo) constantPool.get(((ConstantMethodRefInfo) info).getClassIndex());

                value = ((ConstantUtf8Info) constantPool.get(classInfo.getNameIndex())).getValue();
                if (clazz.equals(value)) {
                    nameAndTypeInfo = (ConstantNameAndTypeInfo) constantPool.get(((ConstantMethodRefInfo) info).getNameAndTypeIndex());

                    value = ((ConstantUtf8Info) constantPool.get(nameAndTypeInfo.getNameIndex())).getValue();
                    if (name.equals(value)) {
                        value = ((ConstantUtf8Info) constantPool.get(nameAndTypeInfo.getDescriptorIndex())).getValue();

                        if (descriptor.equals(value)) {
                            return i;
                        }
                    }
                }

            }
        }


        return -1;
    }

    // ConstantMethodRefInfo -> ConstantClassInfo -> ConstantUtf8Info
    //                       |
    //                       -> ConstantNameAndTypeInfo -> ConstantUtf8Info
    //                                                  |
    //                                                  -> ConstantUtf8Info
}
