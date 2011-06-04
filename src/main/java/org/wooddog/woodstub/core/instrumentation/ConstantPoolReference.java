package org.wooddog.woodstub.core.instrumentation;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 02-06-11
 * Time: 19:50
 * To change this template use File | Settings | File Templates.
 */
public interface ConstantPoolReference {
    int getClassIndex();
    int getNameAndTypeIndex();
}
