package org.wooddog.woodstub.core.instrumentation;

import org.wooddog.woodstub.core.Converter;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 25-04-11
 * Time: 18:36
 * To change this template use File | Settings | File Templates.
 */
public class TableException {
    private int startPc;
    private int endPc;
    private int handlerPc;
    private int catchType;

    public void read(DataInputStream stream) throws IOException {
        startPc = Converter.asUnsigned(stream.readShort());
        endPc = Converter.asUnsigned(stream.readShort());
        handlerPc = Converter.asUnsigned(stream.readShort());
        catchType = Converter.asUnsigned(stream.readShort());
    }

    public void write(DataOutputStream stream) throws IOException {
        stream.writeShort(startPc);
        stream.writeShort(endPc);
        stream.writeShort(handlerPc);
        stream.writeShort(catchType);
    }

    public int getLength() {
        return 8;
    }
}
