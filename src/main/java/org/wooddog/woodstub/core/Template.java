package org.wooddog.woodstub.core;

import org.wooddog.woodstub.core.runtime.Stub;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 24-04-11
 * Time: 00:57
 * To change this template use File | Settings | File Templates.
 */
public class Template {
    /*
    public int methodB(final String a, String b) throws Throwable {
        Stub stub = WoodStub.getStubFactory().createStub(this, "org/wooddog/woodstub/core/Test", "methodB", "(Ljava/lang/String;Ljava/lang/String;)I}");

        if (stub != null) {
            stub.setParameters(new String[]{"a", "b"}, new Object[]{a, b});

            stub.execute();

            switch (stub.getBehavior()) {
                case Stub.RETURN:
                    return ((Integer) stub.getResult()).intValue();

                case Stub.THROW:
                    throw (Throwable) stub.getResult();
            }
        }

        System.out.println("im not stubbed");
        return 0;
    }
    */

    public int methodA(final String a, String b) {
        Stub stub = WoodStub.getStubFactory().createStub(this, "org/wooddog/woodstub/core/Template", "methodA", "(Ljava/lang/String;Ljava/lang/String;)I}");

        if (stub != null) {
            stub.setParameters(null, new Object[]{a, b});

            stub.execute();

            return ((Integer) stub.getResult()).intValue();
        }

        System.out.println("im not stubbed");
        return 0;
    }

}



