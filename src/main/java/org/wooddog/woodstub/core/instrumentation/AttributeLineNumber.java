package org.wooddog.woodstub.core.instrumentation;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 21-05-11
 * Time: 22:09
 * To change this template use File | Settings | File Templates.
 */
public class AttributeLineNumber implements Attribute {
    private int poolIndex;
    private List<TableLineNumber> lineNumberList;


    public AttributeLineNumber() {
        lineNumberList = new ArrayList<TableLineNumber>();
    }

    @Override
    public void setConstantPoolIndex(int index) {
        this.poolIndex = index;
    }

    @Override
    public int getLength() {
        return 8 + (lineNumberList.size() * 4);
    }

    public List<TableLineNumber> getLineNumberList() {
        return lineNumberList;
    }

    public void setLineNumberList(List<TableLineNumber> lineNumberList) {
        this.lineNumberList = lineNumberList;
    }

    @Override
    public void read(ConstantPool constantPool, DataInputStream stream) throws IOException {
        int size;
        int tableSize;
        TableLineNumber lineNumber;

        size = stream.readInt();
        tableSize = stream.readUnsignedShort();

        for (int i = 0; i < tableSize; i++) {
            lineNumber = new TableLineNumber();
            lineNumber.read(stream);
            lineNumberList.add(lineNumber);
        }
    }

    @Override
    public void write(ConstantPool constantPool, DataOutputStream stream) throws IOException {
        stream.writeShort(poolIndex);
        stream.writeInt(getLength() - 6);
        stream.writeShort(lineNumberList.size());
        for(TableLineNumber lineNumber : lineNumberList) {
            lineNumber.write(stream);
        }
    }

    @Override
    public String getName() {
        return "LineNumberTable";
    }
}
