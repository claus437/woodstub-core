package org.wooddog.woodstub.core;

import java.io.DataInputStream;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 23-04-11
 * Time: 22:00
 * To change this template use File | Settings | File Templates.
 */
public class ExceptionEntry {
    private short startPc;
    private short endPc;
    private short handlerPc;
    private short catchType;

    @Override
    public String toString() {
        return "ExceptionEntry{" +
                "startPc=" + startPc +
                ", endPc=" + endPc +
                ", handlerPc=" + handlerPc +
                ", catchType=" + catchType +
                '}';
    }

    public static ExceptionEntry read(DataInputStream stream) throws IOException {
        ExceptionEntry entry;

        entry = new ExceptionEntry();
        entry.startPc = stream.readShort();
        entry.endPc = stream.readShort();
        entry.handlerPc = stream.readShort();
        entry.catchType = stream.readShort();

        return entry;
    }
}
