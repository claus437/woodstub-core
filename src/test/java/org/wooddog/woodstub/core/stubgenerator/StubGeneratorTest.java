package org.wooddog.woodstub.core.stubgenerator;

import org.junit.Assert;
import org.junit.Test;
import org.wooddog.woodstub.core.ClassReader;
import org.wooddog.woodstub.core.IOUtil;
import org.wooddog.woodstub.core.instrumentation.AttributeCode;
import org.wooddog.woodstub.core.instrumentation.ConstantPool;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 02-06-11
 * Time: 14:34
 * To change this template use File | Settings | File Templates.
 */
public class StubGeneratorTest {

    @Test
    public void testStubGetBoolean() throws Exception {
        run("GetBoolean", "getBoolean()Z");
    }

    @Test
    public void testStubGetByte() throws Exception {
        run("GetByte", "getByte()B");
    }

    @Test
    public void testStubGetShort() throws Exception {
        run("GetShort", "getShort()S");
    }

    @Test
    public void testStubGetCharacter() throws Exception {
        run("GetCharacter", "getCharacter()C");
    }

    @Test
    public void testStubGetInteger() throws Exception {
        run("GetInteger", "getInteger()I");
    }

    @Test
    public void testStubGetFloat() throws Exception {
        run("GetFloat", "getFloat()F");
    }

    @Test
    public void testStubGetDouble() throws Exception {
        run("GetDouble", "getDouble()D");
    }

    @Test
    public void testStubGetLong() throws Exception {
        run("GetLong", "getLong()J");
    }

    @Test
    public void testStubGetObject() throws Exception {
        run("GetObject", "getObject()Ljava/lang/Object;");
    }

    @Test
    public void testStubGetString() throws Exception {
        run("GetString", "getString()Ljava/lang/String;");
    }

    @Test
    public void testStubGetArrayOfObjects() throws Exception {
        run("GetArrayOfObjects", "getArrayOfObjects()[Ljava/lang/Object;");
    }

    @Test
    public void testStubGetArrayOfIntegers() throws Exception {
        run("GetArrayOfIntegers", "getArrayOfIntegers()[I");
    }

    @Test
    public void testStubGetMultipleArraysOfObjects() throws Exception {
        run("GetMultipleArraysOfObjects", "getMultipleArraysOfObjects()[[Ljava/lang/Object");
    }

    @Test
    public void testStubGetMultipleArraysOfIntegers() throws Exception {
        run("GetMultipleArraysOfIntegers", "getMultipleArraysOfIntegers()[[I");
    }


    @Test
    public void testStubSetBoolean() throws Exception {
        run("SetBoolean", "setBoolean(Z)V");
    }


    private void run(String test, String method) throws Exception {
        ClassReader reader;
        ConstantPool pool;
        StubGenerator generator;
        InputStream source;
        ByteArrayOutputStream instrumented;
        String expected;
        byte[] stubbedMethod;

        source = getClass().getClassLoader().getResourceAsStream("org/wooddog/woodstub/core/stubgenerator/templates/" + test + ".class");
        instrumented = new ByteArrayOutputStream();

        reader = new ClassReader();
        reader.read(source);
        pool = reader.getConstantPool();

        generator = new StubGenerator(pool, pool.getClassName(reader.getIndexOfClass()));
        stubbedMethod = generator.stub(reader.getMethods().get(1));
        ((AttributeCode) reader.getMethods().get(1).getAttributes("Code").get(0)).setCode(stubbedMethod);
        reader.write(instrumented);


        reader = new ClassReader();
        reader.read(new ByteArrayInputStream(instrumented.toByteArray()));

        expected = new String(IOUtil.read("org/wooddog/woodstub/core/stubgenerator/" + test + ".jbc"));
        expected = expected.replaceAll("\r", "");
        expected = expected.trim();

        Assert.assertEquals(expected, getMethod(method, reader.getSource()));
    }

    public void decompile() throws Exception {
        ClassReader reader;
        String classFile;

        classFile = "org/wooddog/woodstub/core/stubgenerator/TemplateStub.class";

        reader = new ClassReader();
        reader.read(getClass().getClassLoader().getResourceAsStream(classFile));

        System.out.print(reader.getSource());

    }


    private String getMethod(String method, String source) {
        String[] lines;
        StringBuffer methodBlock;
        int elements;

        methodBlock = null;
        elements = 0;
        lines = source.split("\n");

        for (String line : lines) {
            if (line.startsWith("METHOD: " + method)) {
                methodBlock = new StringBuffer();
            }

            if (methodBlock != null) {
                methodBlock.append(line);
                methodBlock.append("\n");

                if (line.isEmpty()) {
                    elements++;
                }
            }

            if (elements == 1) {
                return methodBlock.toString().trim();
            }
        }

        return null;
    }
}
