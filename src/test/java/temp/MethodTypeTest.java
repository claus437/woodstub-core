package temp;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.wooddog.woodstub.core.IOUtil;
import org.wooddog.woodstub.core.WoodStub;
import org.wooddog.woodstub.core.runtime.Stub;
import org.wooddog.woodstub.core.runtime.StubFactory;

/**
 * Created by IntelliJ IDEA.
 * User: dencbr
 * Date: 25-05-11
 * Time: 16:46
 * To change this template use File | Settings | File Templates.
 */
@Ignore
public class MethodTypeTest implements StubFactory, Stub {
    private MethodTypeTestSubject object;
    private boolean stub;


    static {
        IOUtil.loadStubbedClass("target/test-classes", "org/wooddog/woodstub/core/MethodTypeTestSubject");
    }

    @Before
    public void before() {
        WoodStub.setStubFactory(this);
        //WoodStub.resume();
        object = new MethodTypeTestSubject();
    }

    @Test
    public void testStaticMethod() {
       MethodTypeTestSubject.staticMethod();
       Assert.assertEquals("static", MethodTypeTestSubject.invoked);

       stub = true;
       MethodTypeTestSubject.invoked = "unset";

       MethodTypeTestSubject.staticMethod();
       Assert.assertEquals("unset", MethodTypeTestSubject.invoked);
    }


    @Test
    public void testFinalMethod() {
        object.finalMethod();
        Assert.assertEquals("final", MethodTypeTestSubject.invoked);

        stub = true;
        MethodTypeTestSubject.invoked = "unset";

        object.finalMethod();
        Assert.assertEquals("unset", MethodTypeTestSubject.invoked);
    }

    @Test
    public void testStrictFpMethod() {
        object.strictFpMethod();
        Assert.assertEquals("strictfp", MethodTypeTestSubject.invoked);

        stub = true;
        MethodTypeTestSubject.invoked = "unset";

        object.strictFpMethod();
        Assert.assertEquals("unset", MethodTypeTestSubject.invoked);
    }

    @Test
    public void testSynchronizedMethod() {
        object.synchronizedMethod();
        Assert.assertEquals("synchronized", MethodTypeTestSubject.invoked);

        stub = true;
        MethodTypeTestSubject.invoked = "unset";

        object.synchronizedMethod();
        Assert.assertEquals("unset", MethodTypeTestSubject.invoked);
    }

    @Test
    @Ignore
    public void testNativeMethod() {
        object.nativeMethod();
        Assert.assertEquals("native", MethodTypeTestSubject.invoked);

        stub = true;
        MethodTypeTestSubject.invoked = "unset";

        object.nativeMethod();
        Assert.assertEquals("unset", MethodTypeTestSubject.invoked);
    }


    public void setParameters(String[] names, Object[] values) {
        System.out.println("cust para");
    }

    public void execute() throws Throwable {
        System.out.println("cust exec");
    }

    public Object getResult() {
        System.out.println("cust result");
        return "stubbed";
    }

    public Stub createStub(Object source, String name) {
        return stub ? this : null;
    }
}
