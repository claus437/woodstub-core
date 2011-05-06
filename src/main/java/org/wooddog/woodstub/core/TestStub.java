package org.wooddog.woodstub.core;

import org.wooddog.woodstub.core.runtime.Stub;

import java.io.File;
import java.util.Arrays;

/**
 * Created by IntelliJ IDEA.
 * User: DENCBR
 * Date: 05-05-11
 * Time: 20:42
 * To change this template use File | Settings | File Templates.
 */
public class TestStub implements Stub {
    private String method;

    public TestStub(String method) {
        this.method = method;
    }

    public void setParameters(String[] names, Object[] values) {
        System.out.println(Arrays.asList(values));
    }

    public void execute() throws Throwable {

    }

    public int getBehavior() {
        return 0;
    }

    public Object getResult() {
        String type;

        type = method.substring(method.lastIndexOf(")") + 1);

        System.out.println("TYPE: " + type);

        if (type.length() == 1) {
            switch (type.charAt(0)) {
                case 'Z':
                    return new Boolean("false");

                case 'B':
                    return new Byte("1");

                case 'C':
                    return new Character('c');

                case 'S':
                    return new Short("30");

                case 'I':
                    return new Integer(40);

                case 'F':
                    return new Float(50);

                case 'D':
                    return new Double(60);

                case 'V':
                    return null;

                default:
                    throw new InternalErrorException("stub failed no type as " + type);
            }
        }

        if (type.equals("[I")) {
            return new int[]{40,50};
        }

        if (type.startsWith("[")) {
            return new File("c:\\").listFiles();
        }

        return new File("myfile");
    }
}
