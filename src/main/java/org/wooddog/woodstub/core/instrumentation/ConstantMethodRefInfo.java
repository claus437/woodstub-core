package org.wooddog.woodstub.core.instrumentation;

import org.wooddog.woodstub.core.Converter;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 25-04-11
 * Time: 14:34
 * To change this template use File | Settings | File Templates.
 */
public class ConstantMethodRefInfo implements ConstantPoolInfo, ConstantPoolReference {
    private static final int TAG = 10;
    private int classIndex;
    private int nameAndTypeIndex;

    public ConstantMethodRefInfo() {
    }

    public ConstantMethodRefInfo(int classIndex, int nameAndTypeIndex) {
        this.classIndex = classIndex;
        this.nameAndTypeIndex = nameAndTypeIndex;
    }

    public int getTag() {
        return TAG;
    }

    public int getClassIndex() {
        return classIndex;
    }

    public void setClassIndex(int classIndex) {
        this.classIndex = classIndex;
    }

    public int getNameAndTypeIndex() {
        return nameAndTypeIndex;
    }

    public void setNameAndTypeIndex(int nameAndTypeIndex) {
        this.nameAndTypeIndex = nameAndTypeIndex;
    }

    public void read(DataInputStream stream) throws IOException {
        classIndex = stream.readUnsignedShort();
        nameAndTypeIndex = stream.readUnsignedShort();
    }

    public void write(DataOutputStream stream) throws IOException {
        stream.writeByte(TAG);
        stream.writeShort(classIndex);
        stream.writeShort(nameAndTypeIndex);
    }

    public static int indexOf(List<ConstantPoolInfo> constants, int classIndex, int nameAndTypeIndex) {
           ConstantMethodRefInfo info;

           for (int i = 0; i < constants.size(); i++) {

               if (constants.get(i) == null) {
                   continue;
               }

               if (constants.get(i).getTag() == TAG) {
                   info = (ConstantMethodRefInfo) constants.get(i);

                   if (classIndex == info.getClassIndex() && nameAndTypeIndex == info.getNameAndTypeIndex()) {
                       return i;
                   }
               }
           }

           return -1;
    }


    @Override
    public String toString() {
        return "ConstantMethodRefInfo{" +
                "tag=" + TAG +
                ", classIndex=" + classIndex +
                ", nameAndTypeIndex=" + nameAndTypeIndex +
                '}';
    }

    public String[] values() {
        return new String[]{"METHOD_REFERENCE", Integer.toString(classIndex), Integer.toString(nameAndTypeIndex)};
    }

}
