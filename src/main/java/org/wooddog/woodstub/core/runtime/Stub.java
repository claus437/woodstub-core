package org.wooddog.woodstub.core.runtime;

/**
 * This class represents the replacement code of a stubbed method.
 *
 * The execution flow goes in this sequence
 *
 *   1. setParameters(String[], Object[])
 *
 *   2. execute()
 *
 *   3. getResult()
 *
 * though getResult will not be called if the stubbed method has a return type of void.
 */
public interface Stub {

    /**
     * Sets the names and parameters to be handled by this stub.
     *
     * When the method has no arguments the names and values will be zero sized arrays, except for names when the class
     * to be stubbed was compiled without debug enabled, in that case names will be null.
     *
     * @param names The names of the parameters ordered in the same sequence as listed in the method declaration.
     * @param values The values parsed, ordered in the same sequence as listed in the method declaration.
     */
    void setParameters(String[] names, Object[] values);

    /**
     * Executes this stub.
     *
     * Throwing an exception that the stubbed method doesn't declare does not directly cause an error, however it could
     * potentially cause some problems if the calling method is not prepared for it.
     *
     * @throws Throwable Throws any kind of exception for emulating an exception in the stubbed method.
     */
    void execute() throws Throwable;

    /**
     * Gets the result, the stubbed method should return.
     *
     * The returned Object must either match the return type of the stubbed method or inherit the same type, just like
     * normally when returning a method.
     *
     * primitive values most be wrapped with their corresponding wrapper, and most not under any circumstances be null.
     *
     * @return The value to be returned by the stubbed method.
     */
    Object getResult();
}
