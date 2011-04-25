package org.wooddog.woodstub.core;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 24-04-11
 * Time: 23:06
 * To change this template use File | Settings | File Templates.
 */
public class Hex {

    public static void main(String[] args) {

        //System.out.println(Byte.valueOf("7f", 16));
        //System.out.println(Byte.decode("0xbf"));


        int i = Integer.decode("0xbf");

        System.out.println((byte) i);

        //System.out.println(Integer.parseInt("0xff", 16));
        //System.out.println(Byte.parseByte("-7f", 16));
    }
}
