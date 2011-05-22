package org.wooddog.woodstub.core.instrumentation;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 01-05-11
 * Time: 13:29
 * To change this template use File | Settings | File Templates.
 */
public class AttributeLocalVariable implements Attribute {
    private int poolIndex;
    private List<TableEntryLocalVariable> localVariables;

    public AttributeLocalVariable() {
        localVariables = new ArrayList<TableEntryLocalVariable>();
    }

    public void setConstantPoolIndex(int index) {
        this.poolIndex = index;
    }

    public int getLength() {
        return 8 + (localVariables.size() * 10);
    }

    public void read(ConstantPool constantPool, DataInputStream stream) throws IOException {
        int length;
        int tableLength;
        TableEntryLocalVariable variable;

        length = stream.readInt();
        tableLength = stream.readUnsignedShort();

        for (int i = 0; i < tableLength; i++) {
            variable = new TableEntryLocalVariable();
            variable.read(stream);
            localVariables.add(variable);
        }
    }

    public void write(ConstantPool constantPool, DataOutputStream stream) throws IOException {
        stream.writeShort(poolIndex);
        stream.writeInt(getLength() - 6);
        stream.writeShort(localVariables.size());

        for (TableEntryLocalVariable variable : localVariables) {
            variable.write(stream);
        }
    }

    public String getName() {
        return "LocalVariableTable";
    }

    public List<TableEntryLocalVariable> getLocalVariableList() {
        return localVariables;
    }
}
