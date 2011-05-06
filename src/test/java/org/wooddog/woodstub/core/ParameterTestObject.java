package org.wooddog.woodstub.core;

import org.wooddog.woodstub.core.runtime.Stub;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 06-05-11
 * Time: 22:57
 * To change this template use File | Settings | File Templates.
 */
public class ParameterTestObject {
    /*
    public void template(Object b) throws Throwable {
        Stub stub = WoodStub.getStubFactory().createStub(this, "org/wooddog/woodstub/core/runtime/Stub/ParameterTestObject", "template", "(Ljava/lang/Boolean;)Z}");

        if (stub != null) {
            stub.setParameters(null, new Object[]{b}); //

            stub.execute();

            return;
        }

        throw new RuntimeException("not thrown");
    }

    public void parameterBoolean(boolean b) {
        System.out.println("boolean");
    }

    public void parameterByte(byte b) {
        System.out.println("boolean");
    }

    public void parameterChar(char b) {
        System.out.println("boolean");
    }

    public void parameterShort(short b) {
        System.out.println("boolean");
    }

    public void parameterInteger(int b) {
        System.out.println("boolean");
    }

    public void parameterFloat(float b) {
        System.out.println("boolean");
    }

    public void parameterLong(long b) {
        System.out.println("boolean");
    }

    public void parameterDouble(double b) {
        System.out.println("boolean");
    }
    */
    public void parameterObject(Object o) {
        System.out.println("boolean");
    }

    /*
    public void parameterArray(int[] i) {
        System.out.println("boolean");
    }
    */
    /*
    public void parameter2Array(int[][] i) {
        System.out.println("boolean");
    }
    */

}
