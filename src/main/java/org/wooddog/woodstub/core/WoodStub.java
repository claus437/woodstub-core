package org.wooddog.woodstub.core;

import java.lang.instrument.Instrumentation;
import java.util.Arrays;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 22-04-11
 * Time: 18:36
 * To change this template use File | Settings | File Templates.
 */
public class WoodStub {
 	public static void premain(String agentArguments, Instrumentation instrumentation) {
		instrumentation.addTransformer(new WoodTransformer());
	}

    public static boolean isStubbed(String className, String methodName, String descriptor) {
        System.out.println(className + " " + methodName + " " + descriptor);
        return false;
    }

    public static Result evaluate(Object src, String className, String methodName, String descriptor, Object[] values) {
        System.out.println(src);
        System.out.println(className);
        System.out.println(methodName);
        System.out.println(descriptor);
        System.out.println(Arrays.asList(values));

        return null;
    }
}
