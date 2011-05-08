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
        Stub stub = WoodStub.getStubFactory().createStub(this, "org/wooddog/woodstub/core/runtime/Stub/ParameterTestObject", "template", "(Ljava/lang/Object;)V}");

        if (stub != null) {
            stub.setParameters(null, new Object[]{b}); //

            stub.execute();
            stub.getResult();

            return;
        }

        throw new RuntimeException("not thrown");
    }
    */
    public void parameterBoolean(boolean b) {
        System.out.println("z");
    }

    public void parameterByte(byte b) {
        System.out.println("boolean");
    }

    public void parameterChar(char b) {
        System.out.println("c");
    }

    public void parameterShort(short b) {
        System.out.println("s");
    }

    public void parameterInteger(int b) {
        System.out.println("i");
    }

    public void parameterFloat(float b) {
        System.out.println("f");
    }

    public void parameterLong(long b) {
        System.out.println("j");
    }

    public void parameterDouble(double b) {
        System.out.println("d");
    }

    public void parameterObject(Object o) {
        System.out.println("l");
    }

    public void parameterArray(int[] i) {
        System.out.println("[");
    }

    public void parameter2Array(int[][] i) {
        System.out.println("[[");
    }

    public void all(boolean z, byte b){ //, char c){//, short s, int i, float f, long j, double d, Object l, Object[] a) {
        System.out.println("all");
    }

}
