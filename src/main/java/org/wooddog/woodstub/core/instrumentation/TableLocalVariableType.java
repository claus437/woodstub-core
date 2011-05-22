package org.wooddog.woodstub.core.instrumentation;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 22-05-11
 * Time: 17:05
 * To change this template use File | Settings | File Templates.
 */
public class TableLocalVariableType {
    private int startPc;
    private int length;
    private int nameIndex;
    private int signatureIndex;
    private int index;

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

    public int getSignatureIndex() {
        return signatureIndex;
    }

    public void setSignatureIndex(int signatureIndex) {
        this.signatureIndex = signatureIndex;
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
        signatureIndex = stream.readUnsignedShort();
        index = stream.readUnsignedShort();
    }

    public void write(DataOutputStream stream) throws IOException {
        stream.writeShort(startPc);
        stream.writeShort(length);
        stream.writeShort(nameIndex);
        stream.writeShort(signatureIndex);
        stream.writeShort(index);
    }
}
