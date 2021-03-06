package org.wooddog.woodstub.core.instrumentation;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 01-05-11
 * Time: 13:48
 * To change this template use File | Settings | File Templates.
 */
public class TableEntryLocalVariable {
    int startPc;
    int length;
    int nameIndex;
    int descriptorIndex;
    int index;

    public int getStartPc() {
        return startPc;
    }

    public void setStartPc(int startPc) {
        this.startPc = startPc;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getNameIndex() {
        return nameIndex;
    }

    public void setNameIndex(int nameIndex) {
        this.nameIndex = nameIndex;
    }

    public int getDescriptorIndex() {
        return descriptorIndex;
    }

    public void setDescriptorIndex(int descriptorIndex) {
        this.descriptorIndex = descriptorIndex;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void read(DataInputStream stream) throws IOException {
        startPc = stream.readUnsignedShort();
        length = stream.readUnsignedShort();
        nameIndex = stream.readUnsignedShort();
        descriptorIndex = stream.readUnsignedShort();
        index = stream.readUnsignedShort();
    }

    public void write(DataOutputStream stream) throws IOException {
        stream.writeShort(startPc);
        stream.writeShort(length);
        stream.writeShort(nameIndex);
        stream.writeShort(descriptorIndex);
        stream.writeShort(index);
    }

}
