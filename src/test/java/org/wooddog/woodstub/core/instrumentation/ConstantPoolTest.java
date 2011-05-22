package org.wooddog.woodstub.core.instrumentation;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.DataInputStream;
import java.io.IOException;

/**
 *                              | 1 2 3
 * ConstantStringInfo           | N N Y
 *   ConstantUtf8Info           | N Y Y
 *
 *                              | 1 2
 * ConstantUtf8Info             | Y N
 *
 *                              | 1 2 3 4 5
 * ConstantNameAndTypeInfo      | N N N N Y
 *   ConstantUtf8Info (name)    | N Y N Y Y
 *   ConstantUtf8Info (desc)    | N N Y Y Y
 *
 *                              | 1 2 3
 * ConstantClassInfo            | N N Y
 *   ConstantUtf8Info (name)    | N Y Y
 *
 *                              | 1 2 3 4 5
 * ConstantMethodRefInfo        | N N N N Y
 *   ConstantClassInfo          | N Y N Y Y
 *   ConstantNameAndTypeInfo    | N N Y Y Y
 *
 */
public class ConstantPoolTest {
    private ConstantPool pool;

    @Before
    public void init() throws IOException {
        DataInputStream stream;

        stream = new DataInputStream(getClass().getResourceAsStream("ConstantPoolTestClassToVerify.class"));
        stream.readInt();
        stream.readUnsignedShort();
        stream.readUnsignedShort();

        pool = new ConstantPool();
        pool.read(stream);
    }

    @Test
    public void testRead() throws IOException {
        baseAssertions(pool, 26);
        dump(pool);
    }

    @Test
    public void testAddString_ShouldAddNoElements() {
        pool.addString("say");
        baseAssertions(pool, 27);

        Assert.assertEquals("ConstantStringInfo{tag=8, stringIndex=12}", pool.get(26).toString());
    }

    @Test
    public void testAddString_ShouldAdd1Element() {
        pool.addString("say");
        baseAssertions(pool, 27);

        Assert.assertEquals("ConstantStringInfo{tag=8, stringIndex=12}", pool.get(26).toString());
    }

    @Test
    public void testAddString_ShouldAdd2Elements() {
        pool.addString("HelloWorld");
        baseAssertions(pool, 28);

        Assert.assertEquals("ConstantUtf8Info{tag=1, value='HelloWorld'}", pool.get(26).toString());
        Assert.assertEquals("ConstantStringInfo{tag=8, stringIndex=26}", pool.get(27).toString());
    }


    @Test
    public void testAddMethodRef_4_ShouldAdd4Elements() {
        pool.addMethodRef("java/lang/System", "currentTimeMillis", "()J");
        baseAssertions(pool, 26);
    }

    @Test
    public void testAddMethodRef_4_ShouldAdd1Element() {
        pool.addMethodRef("java/lang/Object", "currentTimeMillis", "()J");
        baseAssertions(pool, 27);
        Assert.assertEquals("ConstantMethodRefInfo{tag=10, classIndex=4, nameAndTypeIndex=20}", pool.get(26).toString());
    }


    @Test
    public void testAddMethodRef_3_ShouldAdd4Elements() {
        pool.addMethodRef("java/lang/MyObject", "currentTimeMillis", "()J");
        baseAssertions(pool, 29);

        Assert.assertEquals("ConstantUtf8Info{tag=1, value='java/lang/MyObject'}", pool.get(26).toString());
        Assert.assertEquals("ConstantClassInfo{tag=7, nameIndex=26}", pool.get(27).toString());
        Assert.assertEquals("ConstantMethodRefInfo{tag=10, classIndex=27, nameAndTypeIndex=20}", pool.get(28).toString());
    }

    @Test
    public void testAddMethodRef_2_ShouldAdd4Elements() {
        pool.addMethodRef("java/lang/Object", "mytest", "(J)V");
        baseAssertions(pool, 30);

        Assert.assertEquals("ConstantUtf8Info{tag=1, value='mytest'}", pool.get(26).toString());
        Assert.assertEquals("ConstantUtf8Info{tag=1, value='(J)V'}", pool.get(27).toString());
        Assert.assertEquals("ConstantNameAndTypeInfo{tag=12, nameIndex=26, descriptorIndex=27}", pool.get(28).toString());
        Assert.assertEquals("ConstantMethodRefInfo{tag=10, classIndex=4, nameAndTypeIndex=28}", pool.get(29).toString());
    }


    @Test
    public void testAddMethodRef_1_ShouldAdd6Elements() {
        pool.addMethodRef("java/lang/MyObject", "mytest", "(J)V");
        baseAssertions(pool, 32);

        Assert.assertEquals("ConstantUtf8Info{tag=1, value='java/lang/MyObject'}", pool.get(26).toString());
        Assert.assertEquals("ConstantClassInfo{tag=7, nameIndex=26}", pool.get(27).toString());
        Assert.assertEquals("ConstantUtf8Info{tag=1, value='mytest'}", pool.get(28).toString());
        Assert.assertEquals("ConstantUtf8Info{tag=1, value='(J)V'}", pool.get(29).toString());
        Assert.assertEquals("ConstantNameAndTypeInfo{tag=12, nameIndex=28, descriptorIndex=29}", pool.get(30).toString());
        Assert.assertEquals("ConstantMethodRefInfo{tag=10, classIndex=27, nameAndTypeIndex=30}", pool.get(31).toString());
    }


    @Test
    @Ignore
    public void testAddUtf8_1_ShouldNotModifyPool() {
        pool.addUtf8Info("java/lang/Object");
        baseAssertions(pool, 26);
    }

    @Test
    public void testAddUtf8_2_ShouldAndUtf8String() {
        pool.addUtf8Info("java/lang/MyObject");
        baseAssertions(pool, 27);
        Assert.assertEquals("ConstantUtf8Info{tag=1, value='java/lang/MyObject'}", pool.get(26).toString());
    }


    @Test
    public void testAddNameAndType_1_ShouldAndAllThreeElements() {
        pool.addNameAndType("mytest", "(J)V");
        baseAssertions(pool, 29);
        Assert.assertEquals("ConstantUtf8Info{tag=1, value='mytest'}", pool.get(26).toString());
        Assert.assertEquals("ConstantUtf8Info{tag=1, value='(J)V'}", pool.get(27).toString());
        Assert.assertEquals("ConstantNameAndTypeInfo{tag=12, nameIndex=26, descriptorIndex=27}", pool.get(28).toString());
    }

    @Test
    public void testAddNameAndType_2_ShouldAndTwoElements() {
        pool.addNameAndType("say", "(J)V");
        baseAssertions(pool, 28);
        Assert.assertEquals("ConstantUtf8Info{tag=1, value='(J)V'}", pool.get(26).toString());
        Assert.assertEquals("ConstantNameAndTypeInfo{tag=12, nameIndex=12, descriptorIndex=26}", pool.get(27).toString());
    }

    @Test
    public void testAddNameAndType_3_ShouldAndTwoElements() {
        pool.addNameAndType("mytest", "()V");
        baseAssertions(pool, 28);
        Assert.assertEquals("ConstantUtf8Info{tag=1, value='mytest'}", pool.get(26).toString());
        Assert.assertEquals("ConstantNameAndTypeInfo{tag=12, nameIndex=26, descriptorIndex=6}", pool.get(27).toString());
    }

    @Test
    public void testAddNameAndType_4_ShouldAddOneElement() {
        pool.addNameAndType("say", "()V");
        baseAssertions(pool, 27);
        Assert.assertEquals("ConstantNameAndTypeInfo{tag=12, nameIndex=12, descriptorIndex=6}", pool.get(26).toString());
    }

    @Test
    public void testAddNameAndType_5_ShouldAddNoElements() {
        pool.addNameAndType("currentTimeMillis", "()J");
        baseAssertions(pool, 26);
    }

    @Test
    public void testAddConstantClass_1_ShouldAddTwoElements() {
        pool.addClass("java/lang/MyObject");
        baseAssertions(pool, 28);
        Assert.assertEquals("ConstantUtf8Info{tag=1, value='java/lang/MyObject'}", pool.get(26).toString());
        Assert.assertEquals("ConstantClassInfo{tag=7, nameIndex=26}", pool.get(27).toString());
    }

    @Test
    public void testAddConstantClass_2_ShouldAddOneElement() {
        pool.addClass("say");
        baseAssertions(pool, 27);
        Assert.assertEquals("ConstantClassInfo{tag=7, nameIndex=12}", pool.get(26).toString());
    }

    @Test
    public void testAddConstantClass_3_ShouldAddNoElements() {
       pool.addClass("java/lang/Object");
       baseAssertions(pool, 26);
   }

    private void dump(ConstantPool pool) {
        System.out.println("Assert.assertEquals(" + pool.size() + ", pool.size());");

        for (int i = 0; i < pool.size(); i++) {
            if (pool.get(i) == null) {
                System.out.println("Assert.assertEquals(null, pool.get(" + i + "));");
            } else {
                System.out.println("Assert.assertEquals(\"" + pool.get(i) + "\", pool.get(" + i + ").toString());");
            }
        }
    }

    private void baseAssertions(ConstantPool pool, int expectedSize) {
        Assert.assertEquals(expectedSize, pool.size());
        Assert.assertEquals(null, pool.get(0));
        Assert.assertEquals("ConstantMethodRefInfo{tag=10, classIndex=4, nameAndTypeIndex=18}", pool.get(1).toString());
        Assert.assertEquals("ConstantMethodRefInfo{tag=10, classIndex=19, nameAndTypeIndex=20}", pool.get(2).toString());
        Assert.assertEquals("ConstantClassInfo{tag=7, nameIndex=21}", pool.get(3).toString());
        Assert.assertEquals("ConstantClassInfo{tag=7, nameIndex=22}", pool.get(4).toString());
        Assert.assertEquals("ConstantUtf8Info{tag=1, value='<init>'}", pool.get(5).toString());
        Assert.assertEquals("ConstantUtf8Info{tag=1, value='()V'}", pool.get(6).toString());
        Assert.assertEquals("ConstantUtf8Info{tag=1, value='Code'}", pool.get(7).toString());
        Assert.assertEquals("ConstantUtf8Info{tag=1, value='LineNumberTable'}", pool.get(8).toString());
        Assert.assertEquals("ConstantUtf8Info{tag=1, value='LocalVariableTable'}", pool.get(9).toString());
        Assert.assertEquals("ConstantUtf8Info{tag=1, value='this'}", pool.get(10).toString());
        Assert.assertEquals("ConstantUtf8Info{tag=1, value='Lorg/wooddog/woodstub/core/instrumentation/ConstantPoolTestClassToVerify;'}", pool.get(11).toString());
        Assert.assertEquals("ConstantUtf8Info{tag=1, value='say'}", pool.get(12).toString());
        Assert.assertEquals("ConstantUtf8Info{tag=1, value='(Ljava/lang/String;)V'}", pool.get(13).toString());
        Assert.assertEquals("ConstantUtf8Info{tag=1, value='arg'}", pool.get(14).toString());
        Assert.assertEquals("ConstantUtf8Info{tag=1, value='Ljava/lang/String;'}", pool.get(15).toString());
        Assert.assertEquals("ConstantUtf8Info{tag=1, value='SourceFile'}", pool.get(16).toString());
        Assert.assertEquals("ConstantUtf8Info{tag=1, value='ConstantPoolTestClassToVerify.java'}", pool.get(17).toString());
        Assert.assertEquals("ConstantNameAndTypeInfo{tag=12, nameIndex=5, descriptorIndex=6}", pool.get(18).toString());
        Assert.assertEquals("ConstantClassInfo{tag=7, nameIndex=23}", pool.get(19).toString());
        Assert.assertEquals("ConstantNameAndTypeInfo{tag=12, nameIndex=24, descriptorIndex=25}", pool.get(20).toString());
        Assert.assertEquals("ConstantUtf8Info{tag=1, value='org/wooddog/woodstub/core/instrumentation/ConstantPoolTestClassToVerify'}", pool.get(21).toString());
        Assert.assertEquals("ConstantUtf8Info{tag=1, value='java/lang/Object'}", pool.get(22).toString());
        Assert.assertEquals("ConstantUtf8Info{tag=1, value='java/lang/System'}", pool.get(23).toString());
        Assert.assertEquals("ConstantUtf8Info{tag=1, value='currentTimeMillis'}", pool.get(24).toString());
        Assert.assertEquals("ConstantUtf8Info{tag=1, value='()J'}", pool.get(25).toString());
    }
}
