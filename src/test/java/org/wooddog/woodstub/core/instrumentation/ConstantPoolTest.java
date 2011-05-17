package org.wooddog.woodstub.core.instrumentation;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.DataInputStream;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: DENCBR
 * Date: 17-05-11
 * Time: 18:58
 * To change this template use File | Settings | File Templates.
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
    }


    @Test
    public void testAddMethodReferenceAlreadyPresent_ShouldNotModifyPool () throws IOException {
        pool.addMethodRef("java/lang/System", "currentTimeMillis", "()J");
        baseAssertions(pool, 26);
    }

    @Test
    public void testAddMethodReferenceNoDescriptor_ShouldModifyPool() throws Exception {
        pool.addMethodRef("java/lang/System", "currentTimeMillis", "(I)J");
        baseAssertions(pool, 29);

        Assert.assertEquals("ConstantUtf8Info{tag=1, value='(I)J'}", pool.get(26).toString());
        Assert.assertEquals("ConstantNameAndTypeInfo{tag=12, nameIndex=24, descriptorIndex=26}", pool.get(27).toString());
        Assert.assertEquals("ConstantMethodRefInfo{tag=10, classIndex=19, nameAndTypeIndex=27}", pool.get(28).toString());
    }

    @Test
    public void testAddMethodReferenceNoName_ShouldModifyPool() throws Exception {
        pool.addMethodRef("java/lang/System", "nano", "()J");
        baseAssertions(pool, 29);

        Assert.assertEquals("ConstantUtf8Info{tag=1, value='nano'}", pool.get(26).toString());
        Assert.assertEquals("ConstantNameAndTypeInfo{tag=12, nameIndex=26, descriptorIndex=25}", pool.get(27).toString());
        Assert.assertEquals("ConstantMethodRefInfo{tag=10, classIndex=19, nameAndTypeIndex=27}", pool.get(28).toString());
    }

    @Test
    public void testAddMethodReferenceNoNameAndType_ShouldModifyPool() throws Exception {
        pool.addMethodRef("java/lang/System", "pico", "()S");
        baseAssertions(pool, 30);

        Assert.assertEquals("ConstantUtf8Info{tag=1, value='pico'}", pool.get(26).toString());
        Assert.assertEquals("ConstantUtf8Info{tag=1, value='()S'}", pool.get(27).toString());
        Assert.assertEquals("ConstantNameAndTypeInfo{tag=12, nameIndex=26, descriptorIndex=27}", pool.get(28).toString());
        Assert.assertEquals("ConstantMethodRefInfo{tag=10, classIndex=19, nameAndTypeIndex=28}", pool.get(29).toString());
    }

    @Test
    public void testAddMethodReferenceNoClass_ShouldModifyPool() throws Exception {
        pool.addMethodRef("java/lang/Monster", "pico", "()S");
        baseAssertions(pool, 32);

        Assert.assertEquals("ConstantUtf8Info{tag=1, value='java/lang/Monster'}", pool.get(26).toString());
        Assert.assertEquals("ConstantClassInfo{tag=7, nameIndex=26}", pool.get(27).toString());
        Assert.assertEquals("ConstantUtf8Info{tag=1, value='pico'}", pool.get(28).toString());
        Assert.assertEquals("ConstantUtf8Info{tag=1, value='()S'}", pool.get(29).toString());
        Assert.assertEquals("ConstantNameAndTypeInfo{tag=12, nameIndex=28, descriptorIndex=29}", pool.get(30).toString());
        Assert.assertEquals("ConstantMethodRefInfo{tag=10, classIndex=27, nameAndTypeIndex=30}", pool.get(31).toString());
    }

    /*
    @Test
    public void testAddMethodReferenceNoClassButMethodReference_ShouldModifyPool() throws Exception {
        pool.addMethodRef("java/lang/Monster", "pico", "()S");
        baseAssertions(pool, 32);

        Assert.assertEquals("ConstantUtf8Info{tag=1, value='java/lang/Monster'}", pool.get(26).toString());
        Assert.assertEquals("ConstantClassInfo{tag=7, nameIndex=26}", pool.get(27).toString());
        Assert.assertEquals("ConstantUtf8Info{tag=1, value='pico'}", pool.get(28).toString());
        Assert.assertEquals("ConstantUtf8Info{tag=1, value='()S'}", pool.get(29).toString());
        Assert.assertEquals("ConstantNameAndTypeInfo{tag=12, nameIndex=28, descriptorIndex=29}", pool.get(30).toString());
        Assert.assertEquals("ConstantMethodRefInfo{tag=10, classIndex=27, nameAndTypeIndex=30}", pool.get(31).toString());
    }
    */

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
