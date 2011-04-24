package org.wooddog.woodstub.core.runtime;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 24-04-11
 * Time: 01:01
 * To change this template use File | Settings | File Templates.
 */
public interface StubEvent {

    public Object getSource();

    public String getMethod();

    public String getParameterType(int index);

    public int getParameterInt(int index);

    public String getParameterString(int index);

    public void setReturnValue(Object object);

    public String getReturnType();

    public void raiseException(Throwable t);

    public void skip();
}
