package org.wooddog.woodstub.core;

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
}
