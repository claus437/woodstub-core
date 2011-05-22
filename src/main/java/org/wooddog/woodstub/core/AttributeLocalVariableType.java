package org.wooddog.woodstub.core;

import org.wooddog.woodstub.core.instrumentation.Attribute;
import org.wooddog.woodstub.core.instrumentation.ConstantPool;
import org.wooddog.woodstub.core.instrumentation.TableLocalVariableType;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 22-05-11
 * Time: 17:03
 * To change this template use File | Settings | File Templates.
 */
public class AttributeLocalVariableType implements Attribute {
    private List<TableLocalVariableType> types;
    private int poolIndex;

    public AttributeLocalVariableType() {
        types = new ArrayList<TableLocalVariableType>();
    }

    @Override
    public void setConstantPoolIndex(int index) {
        this.poolIndex = poolIndex;
    }

    @Override
    public int getLength() {
        return 8 + (types.size() * 10);
    }

    @Override
    public void read(ConstantPool constantPool, DataInputStream stream) throws IOException {
        int length;
        int attributes;
        TableLocalVariableType type;

        length = stream.readInt();
        attributes = stream.readUnsignedShort();
        for (int i = 0; i < attributes; i++) {
            type = new TableLocalVariableType();
            type.read(stream);
            types.add(type);
        }

    }

    @Override
    public void write(ConstantPool constantPool, DataOutputStream stream) throws IOException {
        stream.writeShort(poolIndex);
        stream.writeInt(getLength() - 6);
        stream.writeShort(types.size());

        for (TableLocalVariableType type : types) {
            type.write(stream);
        }
    }

    @Override
    public String getName() {
        return "LocalVariableTypeTable";
    }

    public List<TableLocalVariableType> getLocalVariableTypeList() {
        return types;
    }
}
