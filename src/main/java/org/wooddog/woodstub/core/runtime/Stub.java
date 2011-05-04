package org.wooddog.woodstub.core.runtime;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 28-04-11
 * Time: 15:34
 * To change this template use File | Settings | File Templates.
 */
public interface Stub {
    public static final int RETURN = 0;
    public static final int THROW = 1;
    public static final int EVALUATE = 2;

    void setParameters(String[] names, Object[] values);

    void execute() throws Throwable;

    int getBehavior();

    Object getResult();
}
