package org.wooddog.woodstub.core.stubgenerator;

import org.junit.Assert;
import org.junit.Test;
import org.wooddog.woodstub.core.ClassReader;
import org.wooddog.woodstub.core.IOUtil;
import org.wooddog.woodstub.core.asm.Decompile;
import org.wooddog.woodstub.core.instrumentation.StubCodeGenerator;

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
        run("GetBoolean");
    }

    private void run(String test) throws Exception {
        ClassReader reader;
        StubCodeGenerator generator;
        InputStream source;
        ByteArrayOutputStream instrumented;
        String expected;

        source = getClass().getClassLoader().getResourceAsStream("org/wooddog/woodstub/core/stubgenerator/templates/" + test + ".class");
        instrumented = new ByteArrayOutputStream();

        generator = new StubCodeGenerator();
        generator.stubClass("org/wooddog/woodstub/core/stubgenerator/" + test, source, instrumented);

        reader = new ClassReader();
        reader.read(new ByteArrayInputStream(instrumented.toByteArray()));

        expected = new String(IOUtil.read("org/wooddog/woodstub/core/stubgenerator/" + test + ".jbc"));
        Assert.assertEquals(expected, getMethod("getBoolean()Z", reader.getSource()));
    }

    @Test
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

            if (elements == 3) {
                return methodBlock.toString();
            }
        }

        return null;
    }
}
