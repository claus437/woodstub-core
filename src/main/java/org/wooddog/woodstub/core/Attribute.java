package org.wooddog.woodstub.core;

import java.io.DataInputStream;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 23-04-11
 * Time: 19:03
 * To change this template use File | Settings | File Templates.
 */
public interface Attribute {
    void read(ConstantPoolEntry[] constants, short index, DataInputStream stream) throws IOException;
}
