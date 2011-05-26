package org.wooddog.woodstub.core.instrumentation;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: dencbr
 * Date: 26-05-11
 * Time: 14:18
 * To change this template use File | Settings | File Templates.
 */
public class AttributeInnerClass implements Attribute {
    private int poolIndex;
    private List<TableInnerClass> innerClasses;

    public AttributeInnerClass() {
        innerClasses = new ArrayList<TableInnerClass>();
    }

    public void setConstantPoolIndex(int index) {
        this.poolIndex = index;
    }

    public int getLength() {
        return 8 + (innerClasses.size() * 8);
    }

    public void read(ConstantPool constantPool, DataInputStream stream) throws IOException {
        int length;
        int classes;
        TableInnerClass innerClass;

        length = stream.readInt();
        classes = stream.readUnsignedShort();

        for (int i = 0; i < classes; i++) {
            innerClass = new TableInnerClass();
            innerClass.read(stream);
            innerClasses.add(innerClass);
        }
    }

    public void write(ConstantPool constantPool, DataOutputStream stream) throws IOException {
        stream.writeShort(poolIndex);
        stream.writeInt(getLength() - 6);
        stream.writeShort(innerClasses.size());
        for (TableInnerClass innerClass : innerClasses) {
            innerClass.write(stream);
        }
    }

    public String getName() {
        return "InnerClasses";
    }
}
