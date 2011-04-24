package org.wooddog.woodstub.core.runtime;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 24-04-11
 * Time: 01:14
 * To change this template use File | Settings | File Templates.
 */
public class StubEventInternal implements StubEvent {
    void setSource(Object object) {
    }

    void setMethod(String method) {
    }

    void setParameter(int index, Object value) {
    }

    void setReturnType(String type) {
    }

    Object getReturnValue() {
        return null;
    }

    public Object getSource() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getMethod() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getParameterType(int index) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public int getParameterInt(int index) {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getParameterString(int index) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setReturnValue(Object object) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getReturnType() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void raiseException(Throwable t) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void skip() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
