package org.wooddog.woodstub.core.formatter;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 02-06-11
 * Time: 16:35
 * To change this template use File | Settings | File Templates.
 */
public class StringFormatter {

    public static void fill(StringBuffer buffer, int length, char c) {
        for (int i = 0; i < length; i++) {
            buffer.append(c);
        }
    }

    public static void alignRight(StringBuffer out, int width, int value) {
        int length;

        length = out.length();
        out.append(Integer.toString(value));

        while (out.length() < length + width) {
            out.insert(length,' ');
        }
    }
}
