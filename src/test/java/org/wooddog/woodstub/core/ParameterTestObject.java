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
    public void template(boolean z, byte b, double d, char c, short s, int i, float f, long j, Object l, Object[] a) throws Throwable {
        Stub stub = WoodStub.getStubFactory().createStub(this, "org/wooddog/woodstub/core/runtime/Stub/ParameterTestObject", "template", "(Ljava/lang/Object;)V}");

        if (stub != null) {
            stub.setParameters(null, new Object[]{z, b, c, s, i, f, j, d, i, a});

            stub.execute();
            stub.getResult();

            return;
        }

        throw new RuntimeException("not thrown");
    }
    */
    /*
    public void template2() throws Throwable {
        Stub stub = WoodStub.getStubFactory().createStub(this, "org/wooddog/woodstub/core/runtime/Stub/ParameterTestObject", "template", "(Ljava/lang/Object;)V}");

        if (stub != null) {
            stub.setParameters(null, new Object[]{});

            stub.execute();
            stub.getResult();

            return;
        }


        throw new RuntimeException("not thrown");
    }
    */

    /*
                                         //boolean z, byte b, char c, short s, int i, float f, long j, double d, Object l
    public void parameterBoolean(int[] i) throws Throwable {
        Stub stub = WoodStub.getStubFactory().createStub(this, "org/wooddog/woodstub/core/runtime/Stub/ParameterTestObject", "template", "(Ljava/lang/Object;)V}");

        if (stub != null) {
            Object[] o = new Object[1];
            o[0] = i;
            //o[1] = b;
            //o[2] = s;
            //o[3] = i;
            //o[4] = f;
            //o[5] = j;
            //o[6] = d;
            //o[7] = l;


            stub.setParameters(null, o);

            stub.execute();
            stub.getResult();

            return;
        }

        throw new RuntimeException("not thrown");


    }
    */

    public void parameterBoolean(boolean z) {
        System.out.println("boolean " + z);
    }

    public void parameterByte(byte b) {
        System.out.println("byte " + b);
    }

    public void parameterChar(char c) {
        System.out.println("char " + c);
    }

    public void parameterShort(short s) {
        System.out.println("short " + s);
    }

    public void parameterInteger(int i) {
        System.out.println("integer " + i);
    }

    public void parameterFloat(float f) {
        System.out.println("float " + f);
    }

    public void parameterLong(long j) {
        System.out.println("long " + j);
    }
    public void parameterDouble(double b) {
        System.out.println("double " + b);
    }


    public void parameterObject(Object o) {
        System.out.println("object " + o);
    }

    public void parameterArray(int[] i) {
        System.out.println("[integer " + i);
    }


    public void parameter2Array(int[][] i) {
        System.out.println("[[integer " + i);
    }

    public void all(boolean z, byte b) {//, char c, short s, int i, float f, long j, double d, Object l, Object[] a) {
        //System.out.println("all " + z + " " + b + " " + c + " " + s + " " + i + " " + f + " " + j + " " + d + " " + l + a);
    }
    /*
    public void non() throws Throwable {
        System.out.println("non");
    }
    */


}
