package org.wooddog.woodstub.core;

import java.io.DataInputStream;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 24-04-11
 * Time: 13:23
 * To change this template use File | Settings | File Templates.
 */
public class Code {
    public static final byte OP_ALOAD_0 = (byte) 0x2a;
    public static final byte OP_INVOKESPECIAL = (byte) 0xb7;
    public static final byte OP_GETSTATIC = (byte) 0xb2;
    public static final byte OP_BIPUSH = (byte) 0x10;
    public static final byte OP_RETURN = (byte) 0xb1;
    public static final byte OP_INVOKESTATIC = (byte) 0xb8;
    public static final byte OP_INVOKEVIRTUAL = (byte) 0xb6;
    public static final byte OP_ICONST = (byte) 0x02;
    public static final byte OP_ICONST_0 = (byte) 0x03;
    public static final byte OP_ISTORE = (byte) 0x36;
    public static final byte OP_ISTORE_1 = (byte) 0x3c;
    public static final byte OP_ILOAD_1 = (byte) 0x1b;
    public static final byte OP_IF_ICMPGE = (byte) 0xa2;
    public static final byte OP_IICNC = (byte) 0x84;
    public static final byte OP_GOTO = (byte) 0xa7;

    //public static final byte OP_MAXSTACK = (byte)
    //public static final byte OP_MAX_LOCALS = (byte)

    public static void read(DataInputStream stream, int length) throws IOException {
    }
    public static void read2(DataInputStream stream, int length) throws IOException {
        int remaining;
        byte op;

        remaining = length;

        while (remaining > 0) {
            op = stream.readByte();
            remaining --;

            switch (op) {

                case OP_ALOAD_0:
                    System.out.println(length - remaining + " " + toHex(op) + " ALOAD_0");
                    break;

                case OP_INVOKESPECIAL:
                    System.out.println(length - remaining + " " + toHex(op) + " INVOKESPECIAL " + stream.readShort());
                    remaining = remaining - 2;
                    break;

                case OP_GETSTATIC:
                    System.out.println(length - remaining + " " + toHex(op) + " GETSTATIC " + stream.readShort());
                    remaining = remaining -2;
                    break;

                case OP_BIPUSH:
                    System.out.println(length - remaining + " " + toHex(op) + " BIPUSH " + stream.readByte());
                    remaining = remaining -1;
                    break;

                case OP_RETURN:
                    System.out.println(length - remaining + " " + toHex(op) + " RETURN ");
                    break;

                case OP_INVOKESTATIC:
                    System.out.println(length - remaining + " " + toHex(op) + " INVOKESTATIC " + stream.readShort());
                    remaining = remaining - 2;
                    break;

                case OP_INVOKEVIRTUAL:
                    System.out.println(length - remaining + " " + toHex(op) + " INVOKEVIRTUAL " + stream.readShort());
                    remaining = remaining - 2;
                    break;

                case OP_ICONST:
                    System.out.println(length - remaining + " " + toHex(op) + " ICONST ");
                    break;

                case OP_ICONST_0:
                    System.out.println(length - remaining + " " + toHex(op) + " ICONST_0 ");
                    break;

                case OP_ISTORE:
                    System.out.println(length - remaining + " " + toHex(op) + " ISTORE " + stream.readByte());
                    remaining --;
                    break;

                case OP_ISTORE_1:
                    System.out.println(length - remaining + " " + toHex(op) + " ISTORE_1");
                    break;

                case OP_ILOAD_1:
                    System.out.println(length - remaining + " " + toHex(op) + " ILOAD_1");
                    break;

                case OP_IF_ICMPGE:
                    System.out.println(length - remaining + " " + toHex(op) + " IF_ICMPGE " + stream.readShort());
                    remaining = remaining - 2;
                    break;

                case OP_IICNC:
                    System.out.println(length - remaining + " " + toHex(op) + " IICNC " + stream.readByte() + " " + stream.readByte());
                    remaining = remaining - 2;
                    break;

                case OP_GOTO:
                    System.out.println(length - remaining + " " + toHex(op) + " GOTO " + stream.readShort());
                    remaining = remaining - 2;
                    break;

                default:
                    System.out.println(length - remaining + " " + toHex(op) + " UNK");
                    break;
            }

        }

    }

    private static String toHex(byte b) {
        return "0x" + (Integer.toString(( b & 0xff) + 0x100, 16).substring(1)).toUpperCase();
    }
}
