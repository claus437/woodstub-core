package org.wooddog.woodstub.core;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 22-04-11
 * Time: 18:35
 * To change this template use File | Settings | File Templates.
 */
public class WoodTransformer implements ClassFileTransformer {

	public WoodTransformer() {
		super();
	}

	public byte[] transform(ClassLoader loader, String className, Class redefiningClass, ProtectionDomain domain, byte[] bytes) throws IllegalClassFormatException {
		System.out.println("Transformer to Transform Class: " + className);
		return bytes;
	}
}
