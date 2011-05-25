package org.wooddog.woodstub.core;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.wooddog.woodstub.core.runtime.Stub;
import org.wooddog.woodstub.core.runtime.StubFactory;

/**
 * Created by IntelliJ IDEA.
 * User: dencbr
 * Date: 25-05-11
 * Time: 16:46
 * To change this template use File | Settings | File Templates.
 */
public class MethodTypeTest implements StubFactory, Stub {
    private MethodTypeObject object;
    private boolean stub;


    static {
        IOUtil.loadStubbedClass("target/test-classes", "org/wooddog/woodstub/core/MethodTypeObject");
    }

    @Before
    public void before() {
        WoodStub.setStubFactory(this);
        object = new MethodTypeObject();
    }

    @Test
    public void testStaticMethod() {
       MethodTypeObject.staticMethod();
       Assert.assertEquals("static", MethodTypeObject.invoked);

       stub = true;
       MethodTypeObject.invoked = "unset";

       MethodTypeObject.staticMethod();
       Assert.assertEquals("unset", MethodTypeObject.invoked);
    }


    @Test
    public void testFinalMethod() {
        object.finalMethod();
        Assert.assertEquals("final", MethodTypeObject.invoked);

        stub = true;
        MethodTypeObject.invoked = "unset";

        object.finalMethod();
        Assert.assertEquals("unset", MethodTypeObject.invoked);
    }

    @Test
    public void testStrictFpMethod() {
        object.strictFpMethod();
        Assert.assertEquals("strictfp", MethodTypeObject.invoked);

        stub = true;
        MethodTypeObject.invoked = "unset";

        object.strictFpMethod();
        Assert.assertEquals("unset", MethodTypeObject.invoked);
    }

    @Test
    public void testSynchronizedMethod() {
        object.synchronizedMethod();
        Assert.assertEquals("synchronized", MethodTypeObject.invoked);

        stub = true;
        MethodTypeObject.invoked = "unset";

        object.synchronizedMethod();
        Assert.assertEquals("unset", MethodTypeObject.invoked);
    }

    @Test
    @Ignore
    public void testNativeMethod() {
        object.nativeMethod();
        Assert.assertEquals("native", MethodTypeObject.invoked);

        stub = true;
        MethodTypeObject.invoked = "unset";

        object.nativeMethod();
        Assert.assertEquals("unset", MethodTypeObject.invoked);
    }


    public void setParameters(String[] names, Object[] values) {
    }

    public void execute() throws Throwable {
    }

    public Object getResult() {
        return "stubbed";
    }

    public Stub createStub(Object source, String clazz, String name, String description) {
        return stub ? this : null;
    }
}
