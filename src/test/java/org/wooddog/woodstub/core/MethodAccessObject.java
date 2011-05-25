package org.wooddog.woodstub.core;

/**
 * Created by IntelliJ IDEA.
 * User: dencbr
 * Date: 24-05-11
 * Time: 16:44
 * To change this template use File | Settings | File Templates.
 */
public class MethodAccessObject {
    public String invoked = null;

    static {
        System.out.println("im not stubbed");
    }


    public void publicMethod() throws Throwable {
        invoked = "public";
    }

    protected void protectedMethod() {
        invoked = "protected";
    }

    void packageMethod() {
        invoked = "package";
    }

    private void privateMethod() {
        invoked = "private";
    }


}
