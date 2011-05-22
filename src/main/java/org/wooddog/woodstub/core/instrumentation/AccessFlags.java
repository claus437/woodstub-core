package org.wooddog.woodstub.core.instrumentation;

/**
 * Created by IntelliJ IDEA.
 * User: dencbr
 * Date: 19-05-11
 * Time: 09:51
 * To change this template use File | Settings | File Templates.
 */
public class AccessFlags {
    public static final int PUBLIC    = 0x0001;
    public static final int PRIVATE   = 0x0002;
    public static final int PROTECTED = 0x0004;
    public static final int STATIC    = 0x0008;
    public static final int FINAL     = 0x0010;
    public static final int SUPER     = 0x0020;
    public static final int VOLATILE  = 0x0040;
    public static final int TRANSIENT = 0x0080;
    public static final int INTERFACE = 0x0200;
    public static final int ABSTRACT  = 0x0400;


    public static boolean isStatic(int flags) {
        return hasFlag(flags, STATIC);
    }

    public static boolean hasFlag(int flags, int flag) {
        return (flags & flag) != 0;
    }
}
