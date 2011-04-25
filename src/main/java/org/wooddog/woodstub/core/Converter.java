package org.wooddog.woodstub.core;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 25-04-11
 * Time: 16:14
 * To change this template use File | Settings | File Templates.
 */
public class Converter {
    public static int asUnsigned(byte b) {
        int unsigned;

        unsigned = (int) b;
        if (unsigned < 0) {
            unsigned += Byte.MAX_VALUE;
        }

        return unsigned;
    }

    public static int asUnsigned(short s) {
        int unsigned;

        unsigned = (int) s;
        if (unsigned < 0) {
            unsigned += Short.MAX_VALUE;
        }

        return unsigned;
    }

    public static long asUnsigned(int i) {
        long unsigned;

        unsigned = (long) i;
        if (unsigned < 0) {
            unsigned += Integer.MAX_VALUE;
        }

        return unsigned;

    }

}
