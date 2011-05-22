package org.wooddog.woodstub.core.instrumentation;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 21-05-11
 * Time: 22:07
 * To change this template use File | Settings | File Templates.
 */
public class TableLineNumber {
    private int startPc;
    private int lineNumber;

    public int getStartPc() {
        return startPc;
    }

    public void setStartPc(int startPc) {
        this.startPc = startPc;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public void read(DataInputStream stream) throws IOException {
        startPc = stream.readUnsignedShort();
        lineNumber = stream.readUnsignedShort();
    }

    public void write(DataOutputStream stream) throws IOException {
        stream.writeShort(startPc);
        stream.writeShort(lineNumber);
    }

}
