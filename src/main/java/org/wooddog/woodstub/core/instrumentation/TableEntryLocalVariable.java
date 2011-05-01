package org.wooddog.woodstub.core.instrumentation;

import java.io.DataInputStream;
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

    public void read(DataInputStream stream) throws IOException {
        startPc = stream.readUnsignedShort();
        length = stream.readUnsignedShort();
        nameIndex = stream.readUnsignedShort();
        descriptorIndex = stream.readUnsignedShort();
        index = stream.readUnsignedShort();
    }

}
