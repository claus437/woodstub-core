package org.wooddog.woodstub.core.runtime;

/**
 * Factory for creating stubs.
 */
public interface StubFactory {
    Stub createStub(Object source, String clazz, String name, String description);
}
