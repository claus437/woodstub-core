package temp;

import org.wooddog.woodstub.core.WoodStub;

/**
 * Created by IntelliJ IDEA.
 * User: dencbr
 * Date: 25-05-11
 * Time: 16:40
 * To change this template use File | Settings | File Templates.
 */
public class MethodTypeTestSubject {
    public static String invoked = null;


    public static void staticMethod() {
        WoodStub.getStubFactory().createStub(null, null, "method");
        invoked = "static";
    }

    public final void finalMethod() {
        invoked = "final";
    }

    public native void nativeMethod();

    public synchronized void synchronizedMethod() {
        invoked = "synchronized";
    }

    public strictfp void strictFpMethod() {
        invoked = "strictfp";
    }
}
