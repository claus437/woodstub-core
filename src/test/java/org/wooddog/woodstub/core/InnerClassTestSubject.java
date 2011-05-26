package org.wooddog.woodstub.core;

import org.wooddog.woodstub.core.runtime.Stub;

/**
 * Created by IntelliJ IDEA.
 * User: dencbr
 * Date: 26-05-11
 * Time: 10:41
 * To change this template use File | Settings | File Templates.
 */
public class InnerClassTestSubject {

    public String runAnonymousClass(final String value) throws Throwable {
        return new Object() {
            public String mirror(String value) {
                return value.toLowerCase() + "|" + value.toUpperCase();
            }
        }.mirror(value);
    }

    public String runInnerClass(String value) {
        return new MyInnerClass().mirror(value);
    }


    public String runOuterClass(String hello) {
        return new MyOuterClass().mirror(hello);
    }

    private class MyInnerClass {
        public String mirror(String value) {
            return value.toLowerCase() + "/" + value.toUpperCase();
        }
    }
}

class MyOuterClass {
    public String mirror(String value) {
        return value.toLowerCase() + "-" + value.toUpperCase();
    }
}


