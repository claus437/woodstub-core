package org.wooddog.woodstub.core.instrumentation;

import java.io.DataOutputStream;

import java.io.DataInputStream;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: dencbr
 * Date: 26-05-11
 * Time: 14:19
 * To change this template use File | Settings | File Templates.
 */
public class TableInnerClass {
    private int infoIndex;
    private int outerClassInfoIndex;
    private int nameIndex;
    private int accessFlags;

    public int getInfoIndex() {
        return infoIndex;
    }

    public void setInfoIndex(int infoIndex) {
        this.infoIndex = infoIndex;
    }

    public int getOuterClassInfoIndex() {
        return outerClassInfoIndex;
    }

    public void setOuterClassInfoIndex(int outerClassInfoIndex) {
        this.outerClassInfoIndex = outerClassInfoIndex;
    }

    public int getNameIndex() {
        return nameIndex;
    }

    public void setNameIndex(int nameIndex) {
        this.nameIndex = nameIndex;
    }

    public int getAccessFlags() {
        return accessFlags;
    }

    public void setAccessFlags(int accessFlags) {
        this.accessFlags = accessFlags;
    }

    public void read(DataInputStream stream) throws IOException {
        infoIndex = stream.readUnsignedShort();
        outerClassInfoIndex = stream.readUnsignedShort();
        nameIndex = stream.readUnsignedShort();
        accessFlags = stream.readUnsignedShort();
    }

    public void write(DataOutputStream stream) throws IOException {
        stream.writeShort(infoIndex);
        stream.writeShort(outerClassInfoIndex);
        stream.writeShort(nameIndex);
        stream.writeShort(accessFlags);

        System.out.println(toString());
    }

    @Override
    public String toString() {
        return "TableInnerClass{" +
                "infoIndex=" + infoIndex +
                ", outerClassInfoIndex=" + outerClassInfoIndex +
                ", nameIndex=" + nameIndex +
                ", accessFlags=" + accessFlags +
                '}';
    }
}
