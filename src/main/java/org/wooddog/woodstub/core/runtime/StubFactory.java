package org.wooddog.woodstub.core.runtime;

import org.wooddog.woodstub.core.ExecutionTree;

/**
 * Factory for creating stubs.
 */
public interface StubFactory {
    Stub createStub(ExecutionTree tree, Object source, String method);
}
