package org.wooddog.woodstub.core.asm;

import org.wooddog.woodstub.core.InternalErrorException;

import java.io.DataOutputStream;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 13-06-11
 * Time: 19:52
 * To change this template use File | Settings | File Templates.
 */
public class Util {

    public static int computeSize(char[] types) {
        int size;

        size = 0;

        for (char t : types) {
            switch (Character.toUpperCase(t)) {
                case 'B':
                    size ++;
                    break;

                case 'S':
                    size += 2;
                    break;

                case 'I':
                    size += 4;
                    break;

                case 'L':
                    size = -1;
                    break;

                case 'V':
                    size = -1;
                    break;

                case 'W':
                    size = -1;
                    break;

                default:
                    throw new InternalErrorException("unknown type " + t);
            }
        }

        return size;
    }

    public static void write(char type, DataOutputStream stream, int value) {

    }
}
