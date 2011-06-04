package org.wooddog.woodstub.core.instrumentation;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 25-04-11
 * Time: 14:52
 * To change this template use File | Settings | File Templates.
 */
public interface ConstantPoolInfo {
    int getTag();
    void read(DataInputStream stream) throws IOException;
    void write(DataOutputStream stream) throws IOException;
    String[] values();
}
