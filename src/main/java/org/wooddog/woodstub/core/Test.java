package org.wooddog.woodstub.core;

import java.io.File;

/**
 * Created by IntelliJ IDEA.
 * User: DENCBR
 * Date: 02-05-11
 * Time: 22:03
 * To change this template use File | Settings | File Templates.
 */
public class Test {

    public int justATest(String a, String b) {
        if (true) {
            System.out.println("Hello World");
        }

        int i = 10;

        switch (i) {
            case 1:
                System.out.println("C: " + 1);
                break;

            case 5:
                System.out.println("C: " + 5);
                break;

            case 10:
                System.out.println("C: " + 10);
                break;

            default:
                System.out.println("C: " + 20);

        }


        return 10;
    }

    public void nothing() {
    }

    public void integer(int integer, int integer1) {
    }

    public void parameters(boolean a, byte b, char c, int d, float e, double f, int[] g, int[][] h, Object i, Object[] j) {
        System.out.println("im not stubbed");
    }

    public boolean methodBoolean(String a, String b) {
        return true;
    }

    public byte methodByte(String a, String b) {
        return 1;
    }

    public char methodChar(String a, String b) {
        return 2;
    }

    public short methodShort(String a, String b) {
        return 3;
    }

    public int methodInt(String a, String b) {
        return 4;
    }


    public float methodFloat(String a, String b) {
        return 5;
    }

    public double methodDouble(String a, String b) {
        return 6;
    }

    public File methodFile(String a, String b) {
        return new File("file");
    }


    public int[] methodIntArray(String a, String b) {
        return new int[]{1,2};
    }

    public File[] methodFileArray(String a, String b) {
        return new File(".").listFiles();
    }

    public void methodVoid(String a, String b) {
        return;
    }
}
