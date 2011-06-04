package org.wooddog.woodstub.core;

import org.wooddog.woodstub.core.runtime.Stub;

import java.io.File;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 24-04-11
 * Time: 00:57
 * To change this template use File | Settings | File Templates.
 */
public class Template {
    List list;
    private int integer;
    private double d;

    static {

        if (WoodStub.isRunning()) {
            WoodStub.pause();

            try {
                Stub stub = WoodStub.getStubFactory().createStub(null, "org/wooddog/woodstub/core/Test#methodB(Ljava/lang/String;Ljava/lang/String;)I}");

                if (stub != null) {
                    stub.setParameters(new String[]{}, new Object[]{});

                    //stub.execute();
                    // return;
                }
            } finally {
                WoodStub.resume();
            }
        }

        System.out.println("im not stubbed");
    }

    public int[] methodB() throws Throwable {
        if (WoodStub.isRunning()) {
            WoodStub.pause();

            try {
                Stub stub = WoodStub.getStubFactory().createStub(this, "org/wooddog/woodstub/core/Test#methodB(Ljava/lang/String;Ljava/lang/String;)I}");

                if (stub != null) {
                    stub.setParameters(new String[]{}, new Object[]{});

                    stub.execute();

                    return ((int[]) stub.getResult());
                }
            } finally {
                WoodStub.resume();
            }
        }

        System.out.println("im not stubbed");
        return new int[]{};
    }

    public void methodA(boolean a, byte b, char c, int d, float e, double f, int[] g, int[][] h, Object i, Object[] j) throws Throwable {
        if (WoodStub.isRunning()) {
            WoodStub.pause();

            try {
                Stub stub = WoodStub.getStubFactory().createStub(this, "org/wooddog/woodstub/core/Test#methodB(Ljava/lang/String;Ljava/lang/String;)I}");

                if (stub != null) {
                    stub.setParameters(new String[]{}, new Object[]{});

                    stub.execute();
                    return;
                }
            } finally {
                WoodStub.resume();
            }
        }

        System.out.println("im not stubbed");
    }

     public static void methodC() throws Throwable {
        if (WoodStub.isRunning()) {
            WoodStub.pause();

            try {
                Stub stub = WoodStub.getStubFactory().createStub(null, "org/wooddog/woodstub/core/Test#methodB(Ljava/lang/String;Ljava/lang/String;)I}");

                if (stub != null) {
                    stub.setParameters(new String[]{}, new Object[]{});

                    stub.execute();
                    return;
                    //return ((int[]) stub.getResult());
                }
            } finally {
                WoodStub.resume();
            }
        }

        System.out.println("im not stubbed");
        //return new int[]{};
    }

    public int getInteger() throws Throwable {
        if (WoodStub.isRunning()) {
            WoodStub.pause();

            try {
                Stub stub = WoodStub.getStubFactory().createStub(this, "org/wooddog/woodstub/core/GetBoolean#getInteger()I}");

                if (stub != null) {
                    stub.setParameters(new String[]{}, new Object[]{});

                    stub.execute();
                    return ((Integer) stub.getResult()).intValue();
                }
            } finally {
                WoodStub.resume();
            }
        }

        return integer;
    }

    public double getDouble() throws Throwable {

       if (WoodStub.isRunning()) {
           WoodStub.pause();

           try {
               Stub stub = WoodStub.getStubFactory().createStub(this, "org/wooddog/woodstub/core/GetBoolean#getDouble()D}");

               if (stub != null) {
                   stub.setParameters(new String[]{}, new Object[]{});

                   stub.execute();
                   return ((Double) stub.getResult()).doubleValue();
               }
           } finally {
               WoodStub.resume();
           }
       }
       return d;
   }

    public double doDouble(double dob) throws Throwable {
       if (WoodStub.isRunning()) {
           WoodStub.pause();

           try {
               Stub stub = WoodStub.getStubFactory().createStub(this, "org/wooddog/woodstub/core/GetBoolean#getDouble()D}");

               if (stub != null) {
                   stub.setParameters(new String[]{}, new Object[]{dob});

                   stub.execute();

                   return ((Double) stub.getResult()).doubleValue();
               }
           } finally {
               WoodStub.resume();
           }
       }
       return d;
   }
}



