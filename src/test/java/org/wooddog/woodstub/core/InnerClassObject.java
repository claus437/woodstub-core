package org.wooddog.woodstub.core;

import org.wooddog.woodstub.core.runtime.Stub;

/**
 * Created by IntelliJ IDEA.
 * User: dencbr
 * Date: 26-05-11
 * Time: 10:41
 * To change this template use File | Settings | File Templates.
 */
public class InnerClassObject {
/*
    public String myAnonymousInnerClass(final String value) throws Throwable {
        Stub stub = WoodStub.getStubFactory().createStub(this, "org/wooddog/woodstub/core/InnerClassObject", "myAnonymousInnerClass", "(Ljava/lang/String;)Ljava/lang/String;}");

        if (stub != null) {
            Object[] o = new Object[1];
            o[0] = value;

            stub.setParameters(null, o);

            stub.execute();
            return (String) stub.getResult();
        }
        return new Object() {
            public String mirror(String value) {
                System.out.println("value" + value);
                return value.toLowerCase() + "|" + value.toUpperCase();
            }
        }.mirror(value);
    }
*/

    public MyInnerClass newInnerClass() {
        return new MyInnerClass();
    }

    public class MyInnerClass {
        public String mirror(String value) throws Throwable {
            return value.toLowerCase() + "/" + value.toUpperCase();
        }
    }
}


