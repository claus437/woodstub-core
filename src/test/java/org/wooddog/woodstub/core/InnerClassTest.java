package org.wooddog.woodstub.core;

import com.sun.org.apache.bcel.internal.classfile.InnerClass;
import org.junit.Assert;
import org.junit.Test;
import org.wooddog.woodstub.core.runtime.Stub;
import org.wooddog.woodstub.core.runtime.StubFactory;

/**
 * Created by IntelliJ IDEA.
 * User: dencbr
 * Date: 26-05-11
 * Time: 10:41
 * To change this template use File | Settings | File Templates.
 */
public class InnerClassTest implements StubFactory, Stub {
    private boolean stub;

    static {
        IOUtil.loadStubbedClass("target/test-classes", "org/wooddog/woodstub/core/InnerClassObject");
    }

    /*
    @Test
    public void testAnonymousInnerClass() throws Throwable {
        InnerClassObject subject;

        WoodStub.setStubFactory(this);
        subject = new InnerClassObject();

        stub = false;
        Assert.assertEquals("hello|HELLO", subject.myAnonymousInnerClass("hello"));

        stub = true;
        Assert.assertEquals("mirror|rorrim", subject.myAnonymousInnerClass("hello"));
    }
    */
    @Test
    public void testInnerClass() throws Throwable {
        InnerClassObject.MyInnerClass subject;

        WoodStub.setStubFactory(this);
        subject = new InnerClassObject().newInnerClass();

        stub = false;
        Assert.assertEquals("hello/HELLO", subject.mirror("hello"));

        stub = true;
        Assert.assertEquals("mirror|rorrim", subject.mirror("hello"));
    }


    public void setParameters(String[] names, Object[] values) {
    }

    public void execute() throws Throwable {
    }

    public Object getResult() {
        return "mirror|rorrim";
    }

    public Stub createStub(Object source, String clazz, String name, String description) {
        return stub ? this : null;
    }
}
