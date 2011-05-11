package org.wooddog.woodstub.core;

/**
 * Created by IntelliJ IDEA.
 * User: DENCBR
 * Date: 11-05-11
 * Time: 20:16
 * To change this template use File | Settings | File Templates.
 */
public class ToolBox {
    public static char[] getParameterBaseTypes(String descriptor) {
        StringBuffer types;

        types = new StringBuffer();

        int i = 1;

        while (descriptor.charAt(i) != ')') {
            if (descriptor.charAt(i) == '[') {
                while (descriptor.charAt(i) == '[') {
                    i++;
                }

                if (descriptor.charAt(i) == 'L') {
                    i = firstIndexOfNextParameter(i, descriptor);
                } else {
                    i++;
                }

                types.append("L");
                continue;
            }

            if (descriptor.charAt(i) == 'L') {
                i = firstIndexOfNextParameter(i, descriptor);
                types.append('L');
            } else {
                types.append(descriptor.charAt(i));
                i++;
            }
        }

        return types.toString().toCharArray();
    }

    public static int firstIndexOfNextParameter(int index, String descriptor) {
        int i = index;

        while (descriptor.charAt(i) != ';') {
            i++;
        }

        i++;
        return i;
    }


}
