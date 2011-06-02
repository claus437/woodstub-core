package temp;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.wooddog.woodstub.core.ExecutionTree;
import org.wooddog.woodstub.core.IOUtil;
import org.wooddog.woodstub.core.WoodStub;
import org.wooddog.woodstub.core.runtime.Stub;
import org.wooddog.woodstub.core.runtime.StubFactory;

/**
 * Created by IntelliJ IDEA.
 * User: dencbr
 * Date: 26-05-11
 * Time: 10:41
 * To change this template use File | Settings | File Templates.
 */
@Ignore
public class InnerClassTest implements StubFactory, Stub {
    private boolean stub;

    static {
        IOUtil.loadStubbedClass("target/test-classes", "org/wooddog/woodstub/core/MyOuterClass");
        IOUtil.loadStubbedClass("target/test-classes", "org/wooddog/woodstub/core/InnerClassTestSubject$MyInnerClass");
        IOUtil.loadStubbedClass("target/test-classes", "org/wooddog/woodstub/core/InnerClassTestSubject");
    }


    @Test
    /**
     * Tests whether anonymous inner classes behaves ok.
     *
     * expected result:
     *      should return the value defined by the inner class stubbed or not, as we don't create
     *      hooks for this.
     */
    public void testAnonymousInnerClass() throws Throwable {
        InnerClassTestSubject subject;

        WoodStub.setStubFactory(this);
        subject = new InnerClassTestSubject();

        stub = false;
        Assert.assertEquals("hello|HELLO", subject.runAnonymousClass("hello"));

        stub = true;
        Assert.assertEquals("hello|HELLO", subject.runAnonymousClass("hello"));
    }


    @Test
    /**
     * Tests whether named inner classes behaves ok.
     *
     * expected result:
     *      should return the value defined either by the named inner class or by the Stub depending
     *      whether stub is set true.
     */
    public void testInnerClass() throws Throwable {
        InnerClassTestSubject subject;

        WoodStub.setStubFactory(this);
        subject = new InnerClassTestSubject();

        stub = false;
        Assert.assertEquals("hello/HELLO", subject.runInnerClass("hello"));

        stub = true;
        Assert.assertEquals("mirror|rorrim", subject.runInnerClass("hello"));
    }

    @Test
    /**
     * Tests whether outer classes behaves ok.
     *
     * expected result:
     *      should return the value defined either by the outer class or by the Stub depending
     *      whether stub is set true.
     */
    public void testOuterClass() throws Throwable {
        InnerClassTestSubject subject;

        WoodStub.setStubFactory(this);
        subject = new InnerClassTestSubject();

        stub = false;
        Assert.assertEquals("hello-HELLO", subject.runOuterClass("hello"));

        stub = true;
        Assert.assertEquals("mirror|rorrim", subject.runOuterClass("hello"));
    }



    public void setParameters(String[] names, Object[] values) {
    }

    public void execute() throws Throwable {
    }

    public Object getResult() {
        return "mirror|rorrim";
    }

    public Stub createStub(ExecutionTree tree, Object source, String name) {
        return "mirror".equals(name) && stub ? this : null;
    }
}
