package temp;

import org.junit.Assert;
import org.junit.Ignore;
import org.wooddog.woodstub.core.*;
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


@Ignore
public class ParameterAndReturnTest {
    private static final Map<String, Object> VALUES = new HashMap<String, Object>();

    static {
        IOUtil.loadStubbedClass("target/test-classes", "org/wooddog/woodstub/core/ParameterAndReturnTestSubject");

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
    }

    @Test
    public void testCN() {
        System.out.println("CLL " + getClass().getCanonicalName());
        System.out.println("CLL " + getClass().getName());
    }

    @Test
    public void testStaticReturnValues() throws Exception {
        WoodStub.setStubFactory(new StubReturnWriter());

        // Assert.assertEquals(VALUES.get("L"), ParameterAndReturnTestSubject.getStaticObject());
    }

    @Test
    public void testStaticParameterValues() throws Exception {
        WoodStub.setStubFactory(new StubReturnWriter());

        // ParameterAndReturnTestSubject.setStaticObject(VALUES.get("L"));
    }

    @Test
    public void testReturnValues() throws Exception {
        ParameterAndReturnTestSubject objectUnderTest;

        WoodStub.setStubFactory(new StubReturnWriter());
        objectUnderTest = new ParameterAndReturnTestSubject();

        Assert.assertEquals(Boolean.TRUE, objectUnderTest.getBooleanValue());
        Assert.assertEquals(Byte.MAX_VALUE, objectUnderTest.getByteValue());
        Assert.assertEquals(Character.MAX_VALUE, objectUnderTest.getCharValue());
        Assert.assertEquals(Short.MAX_VALUE, objectUnderTest.getShortValue());
        Assert.assertEquals(Integer.MAX_VALUE, objectUnderTest.getIntegerValue());
        Assert.assertEquals(Float.MAX_VALUE, objectUnderTest.getFloatValue(), 0);
        // Assert.assertEquals(Double.MAX_VALUE, objectUnderTest.getDoubleValue(), 0);
        // Assert.assertEquals(Long.MAX_VALUE, objectUnderTest.getLongValue());
        Assert.assertEquals(VALUES.get("L"), objectUnderTest.getObjectValue());
        Assert.assertArrayEquals((int[]) VALUES.get("[I"), objectUnderTest.getArray2DValue());
        Assert.assertArrayEquals((int[][]) VALUES.get("[[I"), objectUnderTest.getArray3DValue());
    }


    @Test
    public void testParameterValues() throws IOException {
        ParameterAndReturnTestSubject objectUnderTest;

        WoodStub.setStubFactory(new StubReturnWriter());
        objectUnderTest = new ParameterAndReturnTestSubject();

        objectUnderTest.setBooleanValue(Boolean.TRUE);
        objectUnderTest.setByteValue(Byte.MAX_VALUE);
        objectUnderTest.setCharValue(Character.MAX_VALUE);
        objectUnderTest.setShortValue(Short.MAX_VALUE);
        objectUnderTest.setIntegerValue(Integer.MAX_VALUE);
        objectUnderTest.setFloatValue(Float.MAX_VALUE);
        objectUnderTest.setDoubleValue(Double.MAX_VALUE);
        //objectUnderTest.setLongValue(Long.MAX_VALUE);
        objectUnderTest.setObjectValue(VALUES.get("L"));
        objectUnderTest.setArray2DValue((int[]) VALUES.get("[I"));
        objectUnderTest.setArray3DValue((int[][]) VALUES.get("[[I"));
        //objectUnderTest.setAll(Boolean.TRUE, Byte.MAX_VALUE, Character.MAX_VALUE, Short.MAX_VALUE, Integer.MAX_VALUE, Float.MAX_VALUE, Double.MAX_VALUE, Long.MAX_VALUE, VALUES.get("L"), (int[]) VALUES.get("[I"), (int[][]) VALUES.get("[[I"));
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

        public void execute() {
        }

        public Object getResult() {
            Pattern pattern;
            Matcher matcher;

            pattern = Pattern.compile("\\)([ZBCSFIDJL\\[]+)");
            matcher = pattern.matcher(description);

            if (!matcher.find()) {
                return null;
            }

            System.out.println(matcher.group(1) + " " + VALUES.get(matcher.group(1)));
            return VALUES.get(matcher.group(1));
        }

        public Stub createStub(Object source, String method) {
            StubReturnWriter stub;

            System.out.println(method);
            stub = new StubReturnWriter();
            stub.description = description;

            return stub;
        }
    }
}
