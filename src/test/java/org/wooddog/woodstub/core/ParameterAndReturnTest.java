package org.wooddog.woodstub.core;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.wooddog.woodstub.core.instrumentation.StubCodeGenerator;
import org.wooddog.woodstub.core.runtime.Stub;
import org.wooddog.woodstub.core.runtime.StubFactory;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by IntelliJ IDEA.
 * User: DENCBR
 * Date: 11-05-11
 * Time: 23:58
 * To change this template use File | Settings | File Templates.
 */
import org.junit.Test;


public class ParameterAndReturnTest {
    private static final Map<String, Object> VALUES = new HashMap<String, Object>();

    @BeforeClass
    public static void init() throws Exception {
        VALUES.put("Z", Boolean.TRUE);
        VALUES.put("B", Byte.MAX_VALUE);
        VALUES.put("C", Character.MAX_VALUE);
        VALUES.put("S", Short.MAX_VALUE);
        VALUES.put("I", Integer.MAX_VALUE);
        VALUES.put("F", Float.MAX_VALUE);
        VALUES.put("D", Double.MAX_VALUE);
        VALUES.put("J", Long.MAX_VALUE);
        VALUES.put("L", new Object());
        VALUES.put("[I", new int[]{Integer.MIN_VALUE, Integer.MAX_VALUE});
        VALUES.put("[[I", new int[][]{{Integer.MIN_VALUE, Integer.MAX_VALUE}, {Integer.MIN_VALUE, Integer.MAX_VALUE}});

        installStubbedValueObjectClass();
        WoodStub.setStubFactory(new StubReturnWriter());
    }


    @Test
    public void testReturnValues() throws Exception {
        ValueObject objectUnderTest;

        objectUnderTest = new ValueObject();
        Assert.assertEquals(objectUnderTest.getBooleanValue(), Boolean.TRUE);
        Assert.assertEquals(objectUnderTest.getByteValue(), Byte.MAX_VALUE);
        Assert.assertEquals(objectUnderTest.getCharValue(), Character.MAX_VALUE);
        Assert.assertEquals(objectUnderTest.getShortValue(), Short.MAX_VALUE);
        Assert.assertEquals(objectUnderTest.getIntegerValue(), Integer.MAX_VALUE);
        Assert.assertEquals(objectUnderTest.getFloatValue(), Float.MAX_VALUE, 0);
        Assert.assertEquals(objectUnderTest.getDoubleValue(), Double.MAX_VALUE, 0);
        Assert.assertEquals(objectUnderTest.getLongValue(), Long.MAX_VALUE);
        Assert.assertEquals(objectUnderTest.getObjectValue(), VALUES.get("L"));
        Assert.assertArrayEquals(objectUnderTest.getArray2DValue(), (int[]) VALUES.get("[I"));
        Assert.assertArrayEquals(objectUnderTest.getArray3DValue(), (int[][]) VALUES.get("[[I"));
    }


    @Test
    public void testParameterValues() throws IOException {
        ValueObject objectUnderTest;

        objectUnderTest = new ValueObject();
        objectUnderTest.setBooleanValue(Boolean.TRUE);
        objectUnderTest.setByteValue(Byte.MAX_VALUE);
        objectUnderTest.setCharValue(Character.MAX_VALUE);
        objectUnderTest.setShortValue(Short.MAX_VALUE);
        objectUnderTest.setIntegerValue(Integer.MAX_VALUE);
        objectUnderTest.setFloatValue(Float.MAX_VALUE);
        objectUnderTest.setDoubleValue(Double.MAX_VALUE);
        objectUnderTest.setLongValue(Long.MAX_VALUE);
        objectUnderTest.setObjectValue(VALUES.get("L"));
        objectUnderTest.setArray2DValue((int[]) VALUES.get("[I"));
        objectUnderTest.setArray3DValue((int[][]) VALUES.get("[[I"));
        objectUnderTest.setAll(Boolean.TRUE, Byte.MAX_VALUE, Character.MAX_VALUE, Short.MAX_VALUE, Integer.MAX_VALUE, Float.MAX_VALUE, Double.MAX_VALUE, Long.MAX_VALUE, VALUES.get("L"), (int[]) VALUES.get("[I"), (int[][]) VALUES.get("[[I"));
    }


    public static class StubReturnWriter implements Stub, StubFactory {
        private String description;

        public void setParameters(String[] names, Object[] values) {
            char[] types;

            types = ToolBox.getParameterBaseTypes(description);
            for (int i = 0; i < types.length; i++) {

                if (values[i] instanceof int[]) {
                    Assert.assertArrayEquals("array did not match for " + description, ((int[]) VALUES.get("[I")), (int[]) values[i]);
                    continue;
                }

                if (values[i] instanceof int[][]) {
                    Assert.assertArrayEquals("array did not match for " + description, ((int[][]) VALUES.get("[[I")), (int[][]) values[i]);
                    continue;
                }

                Assert.assertEquals("value did not match for " + description, VALUES.get(Character.toString(types[i])), values[i]);
            }
        }

        public void execute() throws Throwable {
        }

        public Object getResult() {
            Pattern pattern;
            Matcher matcher;

            pattern = Pattern.compile("\\)([ZBCSFIDJL\\[]+)");
            matcher = pattern.matcher(description);

            if (!matcher.find()) {
                return null;
            }

            return VALUES.get(matcher.group(1));
        }

        public Stub createStub(Object source, String clazz, String name, String description) {
            StubReturnWriter stub;

            stub = new StubReturnWriter();
            stub.description = description;

            return stub;
        }
    }

    private static void installStubbedValueObjectClass() throws IOException {
           StubCodeGenerator stubGenerator;
           FileInputStream source;
           FileOutputStream target;

           source = new FileInputStream(new File("src/test/resources/org/wooddog/woodstub/core/CleanValueObject.class"));
           target= new FileOutputStream(new File("target/test-classes/org/wooddog/woodstub/core/ValueObject.class"));

           try {
               stubGenerator = new StubCodeGenerator();
               stubGenerator.stubClass(source, target);
           } finally {
               try {
                   source.close();
               } finally {
                   target.close();
               }
           }
       }
}
