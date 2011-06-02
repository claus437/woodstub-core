package temp;

import org.junit.Assert;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Before;
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
 * Date: 24-05-11
 * Time: 17:07
 * To change this template use File | Settings | File Templates.
 */
@Ignore
public class MethodAccessTest implements StubFactory, Stub {
    private MethodAccessTestSubject object;
    private boolean stub;


    static {
        WoodStub.setStubFactory(new StubFactory() {
            public Stub createStub(ExecutionTree tree, Object source, String name) {
                System.out.println(source + " " + name);
                return new Stub() {
                    public void setParameters(String[] names, Object[] values) {
                        //To change body of implemented methods use File | Settings | File Templates.
                    }

                    public void execute() throws Throwable {
                        System.out.println("replaced logic");
                    }

                    public Object getResult() {
                        return null;  //To change body of implemented methods use File | Settings | File Templates.
                    }
                };
            }
        });
        IOUtil.loadStubbedClass("target/test-classes", "org/wooddog/woodstub/core/MethodAccessTestSubject");
    }

    @Before
    public void before() {
        WoodStub.setStubFactory(this);
        object = new MethodAccessTestSubject();
    }

    @Test
    public void testPublicMethod() {
        invoke(object, "publicMethod");
        Assert.assertEquals("public", object.invoked);

        stub = true;
        object.invoked = "unset";
        invoke(object, "publicMethod");
        Assert.assertEquals("unset", object.invoked);
    }

    @Test
    public void testProtectedMethod() {
        invoke(object, "protectedMethod");
        Assert.assertEquals("protected", object.invoked);

        stub = true;
        object.invoked = "unset";
        invoke(object, "protectedMethod");
        Assert.assertEquals("unset", object.invoked);
    }

    @Test
    public void testPackageMethod() {
        invoke(object, "packageMethod");
        Assert.assertEquals("package", object.invoked);

        stub = true;
        object.invoked = "unset";
        invoke(object, "packageMethod");
        Assert.assertEquals("unset", object.invoked);
    }

    @Test
    public void testPrivateMethod() {
        invoke(object, "privateMethod");
        Assert.assertEquals("private", object.invoked);

        stub = true;
        object.invoked = "unset";
        invoke(object, "privateMethod");
        Assert.assertEquals("unset", object.invoked);
    }

    public Object invoke(Object object, String methodName, Object... parameters) {
        Class[] types;
        Method method;

        types = getParameterTypes(parameters);

        try {
            method = object.getClass().getDeclaredMethod(methodName, types);
            method.setAccessible(true);
        } catch (NoSuchMethodException x) {
            throw new RuntimeException("method " + methodName + " not found on " + object + " " + x.getMessage(), x);
        }

        try {
            return method.invoke(object, parameters);
        } catch (IllegalAccessException x) {
            throw new RuntimeException(x);
        } catch (InvocationTargetException x) {
            throw new RuntimeException(x);
        }
    }

    public Class[] getParameterTypes(Object... values) {
        Class[] types;

        if (values == null) {
            return new Class[0];
        }

        types = new Class[values.length];
        for (int i = 0; i < values.length; i++) {
            types[i] = values[i].getClass();
        }

        return types;
    }



    public void setParameters(String[] names, Object[] values) {
    }

    public void execute() throws Throwable {
    }

    public Object getResult() {
        return "stubbed";
    }

    public Stub createStub(ExecutionTree tree, Object source, String methodName) {
        return stub ? this : null;
    }
}
