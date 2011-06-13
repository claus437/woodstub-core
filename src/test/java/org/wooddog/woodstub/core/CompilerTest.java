package org.wooddog.woodstub.core;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.wooddog.woodstub.core.asm.Compiler;
import org.wooddog.woodstub.core.instrumentation.ConstantPool;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
/**
 * Created by IntelliJ IDEA.
 * User: dencbr
 * Date: 30-05-11
 * Time: 17:01
 * To change this template use File | Settings | File Templates.
 */
public class CompilerTest {
    private static final int ALOAD = 25;
    private static final int LDC = 18;
    private static final int GOTO = 167;
    private static final int INVOKE_STATIC = 184;
    private static final int A_NEW_ARRAY = 189;

    private ByteDataOutputStream expected;
    private Compiler compiler;
    private ConstantPool pool;
    private byte[] code;

    @Before
    public void init() {
        pool = new ConstantPool();
        compiler = new Compiler(pool);

        expected = new ByteDataOutputStream(new ByteArrayOutputStream());
    }


    @Test
    public void testLoadStringFromPool() throws Exception {
        expected.writeByte(LDC);
        expected.writeByte(2);

        compiler.add("ldc", "Something");

        code = compiler.compile();

        Assert.assertArrayEquals(expected.getBytes(), code);
        Assert.assertEquals(3, pool.size());
        Assert.assertArrayEquals(new String[]{"UTF8", "Something"}, pool.get(1).values());
        Assert.assertArrayEquals(new String[]{"STRING", "1"}, pool.get(2).values());
    }

    @Test
    public void testInvokeStaticMethodInPool() throws Exception {
        expected.writeByte(INVOKE_STATIC);
        expected.writeShort(6);

        compiler.add("invokestatic", "org/Test#doSomething(I)V");

        code = compiler.compile();

        Assert.assertArrayEquals(expected.getBytes(), code);
        Assert.assertEquals(7, pool.size() );
        Assert.assertArrayEquals(new String[]{"UTF8", "org/Test"}, pool.get(1).values());
        Assert.assertArrayEquals(new String[]{"CLASS", "1"}, pool.get(2).values());
        Assert.assertArrayEquals(new String[]{"UTF8", "doSomething(I)"}, pool.get(3).values());
        Assert.assertArrayEquals(new String[]{"UTF8", "V"}, pool.get(4).values());
        Assert.assertArrayEquals(new String[]{"NAME_AND_TYPE", "3", "4"}, pool.get(5).values());
        Assert.assertArrayEquals(new String[]{"METHOD_REFERENCE", "2", "5"}, pool.get(6).values());
    }

    @Test
    public void testANewArrayInConstantPool() throws Exception {
        expected.writeByte(A_NEW_ARRAY);
        expected.writeShort(2);

        compiler.add("anewarray", "org/Test");

        code = compiler.compile();

        Assert.assertArrayEquals(expected.getBytes(), code);

        Assert.assertEquals(3, pool.size());
        Assert.assertArrayEquals(new String[]{"UTF8", "org/Test"}, pool.get(1).values());
        Assert.assertArrayEquals(new String[]{"CLASS", "1"}, pool.get(2).values());
    }

    @Test
    public void testALoadWithValue() throws Exception {
        expected.writeByte(ALOAD);
        expected.writeByte(1);

        compiler.add("aload", 1);

        code = compiler.compile();

        Assert.assertArrayEquals(expected.getBytes(), code);
    }

    @Test
    public void testGotoStart() throws Exception {
        // 01 234
        // AB GBB
        expected.writeByte(ALOAD);
        expected.writeByte(1);
        expected.writeByte(GOTO);
        expected.writeShort(-2);

        compiler.add("label", "TEST");  // -
        compiler.add("aload", 1);       // 0
        compiler.add("goto", "TEST");   // 2

        code = compiler.compile();

        Assert.assertArrayEquals(expected.getBytes(), code);
    }

    @Test
    public void testGotoPostEnd() throws Exception {
        // 012 34 5
        // GBB AB -
        expected.writeByte(GOTO);       // 0
        expected.writeShort(5);         // 1
        expected.writeByte(ALOAD);      // 3
        expected.writeByte(1);          // 4
                                        // 5

        compiler.add("goto", "END");
        compiler.add("aload", 1);

        code = compiler.compile();

        Assert.assertArrayEquals(expected.getBytes(), code);
    }


    @Test
    public void testGotoForward() throws Exception {
        // 01 234 56 78
        // AB GSS AB AB
        expected.writeByte(ALOAD);
        expected.writeByte(1);
        expected.writeByte(GOTO);
        expected.writeShort(5);
        expected.writeByte(ALOAD);
        expected.writeByte(2);
        expected.writeByte(ALOAD);
        expected.writeByte(3);

        compiler.add("aload", 1);           // 0
        compiler.add("goto", "FORWARD");    // 3
        compiler.add("aload", 2);           // 5
        compiler.add("label", "FORWARD");   // -
        compiler.add("aload", 3);           // 7

        code = compiler.compile();

        Assert.assertArrayEquals(expected.getBytes(), code);
    }

    @Test
    public void testGotoBackward() throws Exception {
        // 01 23 456 78
        // AB AB GBB AB
        expected.writeByte(ALOAD);
        expected.writeByte(1);
        expected.writeByte(ALOAD);
        expected.writeByte(2);
        expected.writeByte(GOTO);
        expected.writeShort(-2);
        expected.writeByte(ALOAD);
        expected.writeByte(3);

        compiler.add("aload", 1);           // 0
        compiler.add("label", "BACKWARD");  // -
        compiler.add("aload", 2);           // 2
        compiler.add("goto", "BACKWARD");   // 4
        compiler.add("aload", 3);           // 7

        code = compiler.compile();

        Assert.assertArrayEquals(expected.getBytes(), code);
    }

    private void dump(byte[] bytes) {
        for (Byte b : bytes) {
            System.out.println(b + ", ");
        }
    }


    class ByteDataOutputStream extends DataOutputStream {
        ByteArrayOutputStream stream;

        public ByteDataOutputStream(ByteArrayOutputStream stream) {
            super(stream);
            this.stream = stream;
        }

        public byte[] getBytes() {
            return stream.toByteArray();
        }
    }

    @Test
    @Ignore
    public void decompile() throws Exception {
        ClassReader reader;

        reader = new ClassReader();
        reader.read(getClass().getClassLoader().getResourceAsStream("org/wooddog/woodstub/core/stubgenerator/templates/GetBooleanExpected.class"));
        System.out.println(reader.getSource());

    }
}


