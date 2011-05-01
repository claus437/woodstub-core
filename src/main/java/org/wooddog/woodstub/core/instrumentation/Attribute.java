package org.wooddog.woodstub.core.instrumentation;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 25-04-11
 * Time: 16:46
 * To change this template use File | Settings | File Templates.
 */
public interface Attribute {
    void setConstantPoolIndex(int index);

    int getLength();

    void read(ConstantPool constantPool, DataInputStream stream) throws IOException;

    void write(ConstantPool constantPool, DataOutputStream stream) throws IOException;

    String getName();
}
