package org.wooddog.woodstub.core;

import com.sun.org.apache.xerces.internal.impl.xs.identity.ValueStore;
import org.junit.Assert;
import org.wooddog.woodstub.core.instrumentation.StubCodeGenerator;

import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.wooddog.woodstub.core.runtime.Stub;
import org.wooddog.woodstub.core.runtime.StubFactory;


/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 06-05-11
 * Time: 22:56
 * To change this template use File | Settings | File Templates.
 */
public class ParameterTest implements StubFactory {
    private static final Map<String, Object> VALUES = new HashMap<String, Object>();

    static {
        VALUES.put("Z", Boolean.FALSE);
        VALUES.put("B", Byte.MIN_VALUE);
        VALUES.put("C", Character.MIN_VALUE);
        VALUES.put("S", Short.MIN_VALUE);
        VALUES.put("I", Integer.MIN_VALUE);
        VALUES.put("F", Float.MIN_VALUE);
        VALUES.put("D", Double.MIN_VALUE);
        VALUES.put("J", Long.MIN_VALUE);
        VALUES.put("L", new Object());
        VALUES.put("[I", new int[]{Integer.MIN_VALUE, Integer.MAX_VALUE});
    }

    @Test
    public void testParameters() throws IOException {
        ParameterTestObject objectUnderTest;
        StubCodeGenerator stubGenerator;
        File sourceFile;
        File targetFile;
        InputStream source;
        OutputStream target;

        sourceFile = new File("src/test/java//org/wooddog/woodstub/core/ParameterTestObject.class");
        sourceFile.setLastModified(System.currentTimeMillis());

        sourceFile = new File("target/test-classes/org/wooddog/woodstub/core/ParameterTestObject.class");
        targetFile = new File("target/classes/org/wooddog/woodstub/core/ParameterTestObject.class");

        source = new FileInputStream(sourceFile);
        target = new FileOutputStream(targetFile);

        stubGenerator = new StubCodeGenerator();
        stubGenerator.stubClass(source, target);

        source.close();
        target.close();

        sourceFile.delete();
        targetFile.renameTo(sourceFile);

        WoodStub.setStubFactory(new StubParameterVerifier());

        objectUnderTest = new ParameterTestObject();
        objectUnderTest.setBooleanValue(Boolean.FALSE);
        objectUnderTest.setByteValue(Byte.MIN_VALUE);
        objectUnderTest.setCharValue(Character.MIN_VALUE);
        objectUnderTest.setShortValue(Short.MIN_VALUE);
        objectUnderTest.setIntegerValue(Integer.MIN_VALUE);
        objectUnderTest.setFloatValue(Float.MIN_VALUE);
        objectUnderTest.setDoubleValue(Double.MIN_VALUE);
        objectUnderTest.setLongValue(Long.MIN_VALUE);
        objectUnderTest.setObjectValue(VALUES.get("L"));
        objectUnderTest.setArray2DValue((int[]) VALUES.get("[I"));



        /*
        p.parameterFloat(10);
        p.parameterLong(20);
        p.parameterDouble(30);
        */
        /*
        p.parameterObject(new Object());
        p.parameterArray(new int[]{1});
        p.parameter2Array(new int[1][2]);
        p.all(true, Byte.MAX_VALUE, 'c', Short.MAX_VALUE, 20, 0.1f, 30L, 50D, new Object(), new Object[]{});
        */
    }

    public class StubParameterVerifier implements Stub, StubFactory {
        Object source;
        String clazz;
        String name;
        String description;
        String[] names;
        Object[] values;

        public Stub createStub(Object source, String clazz, String name, String description) {
            return name.startsWith("set") ? new StubParameterVerifier(source, clazz, name, description) : null;
        }

        StubParameterVerifier() {
        }

        public StubParameterVerifier(Object source, String clazz, String name, String description) {
            this.source = source;
            this.clazz = clazz;
            this.name = name;
            this.description = description;
        }

        public void setParameters(String[] names, Object[] values) {
            this.names = names;
            this.values = values;
        }

        public void execute() throws Throwable {
            char[] types;

            types = ToolBox.getParameterBaseTypes(description);
            for (int i = 0; i < types.length; i++) {

                if (values[i] instanceof int[]) {
                    Assert.assertArrayEquals("array did not match for " + name + description, ((int[]) VALUES.get("[I")), (int[]) values[i]);
                } else {
                    Assert.assertEquals("value did not match for " + name + description, VALUES.get(Character.toString(types[i])), values[i]);
                }
            }
        }

        public Object getResult() {
            return null;
        }
    }

    public Stub createStub(Object source, String clazz, String name, String description) {
        return new StubParameterVerifier(source, clazz, name, description);
    }
}
