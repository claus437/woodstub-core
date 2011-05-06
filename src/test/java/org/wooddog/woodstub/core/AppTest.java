package org.wooddog.woodstub.core;


import org.junit.Test;

import java.util.Arrays;

/**
 * Unit test for simple App.
 */
public class AppTest {

    @Test
    public void testA() {
        org.wooddog.woodstub.core.Test t = new org.wooddog.woodstub.core.Test();
        System.out.println(t.justATest("var1", "var2"));
        System.out.println(t.methodBoolean("a", "b"));
        System.out.println(t.methodByte("a", "b"));
        System.out.println(t.methodChar("a", "b"));
        System.out.println(t.methodShort("a", "b"));
        System.out.println(t.methodInt("a", "b"));
        System.out.println(t.methodFloat("a", "b"));
        System.out.println(t.methodDouble("a", "b"));
        System.out.println(t.methodFile("a", "b"));
        System.out.println((t.methodIntArray("a", "b")[1]));
        System.out.println(Arrays.asList(t.methodFileArray("a", "b")));
        //t.parameters(true, Byte.MAX_VALUE,'c', 20, 30, 40, new int[]{}, new int[][]{},new Object(), new Object[]{});
        t.methodVoid("a", "b");
    }


}
