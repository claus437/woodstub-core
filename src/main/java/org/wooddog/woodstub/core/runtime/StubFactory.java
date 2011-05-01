package org.wooddog.woodstub.core.runtime;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 29-04-11
 * Time: 17:54
 * To change this template use File | Settings | File Templates.
 */
public interface StubFactory {
    Stub createStub(Object source, String clazz, String name, String description);
}
