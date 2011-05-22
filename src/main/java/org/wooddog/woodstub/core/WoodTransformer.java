package org.wooddog.woodstub.core;

import org.wooddog.woodstub.core.instrumentation.StubCodeGenerator;
import sun.security.krb5.internal.LoginOptions;

import java.io.*;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 22-04-11
 * Time: 18:35
 * To change this template use File | Settings | File Templates.
 */
public class WoodTransformer implements ClassFileTransformer {
    private static final Logger LOGGER = Logger.getLogger(WoodTransformer.class.getName());

	public WoodTransformer() {
		super();

        MyLogger.init();
    }


	public byte[] transform(ClassLoader loader, String className, Class redefiningClass, ProtectionDomain domain, byte[] bytes) throws IllegalClassFormatException {
        StubCodeGenerator stubGenerator;
        ByteArrayInputStream source;
        ByteArrayOutputStream target;

        if (loader.getResourceAsStream("org/wooddog/woodstub/core/WoodStub.class") == null) {
            LOGGER.log(Level.FINE, "skipping " +  className + " on loader " + loader + " as found no client lib");
            return bytes;
        }

        source = new ByteArrayInputStream(bytes);
        target = new ByteArrayOutputStream();

        try {
            MyLogger.write(className, bytes);
            stubGenerator = new StubCodeGenerator();
            stubGenerator.stubClass(className, source, target);
            MyLogger.write(className + "_WS", target.toByteArray());
            LOGGER.log(Level.FINE, "stubbed " +  className + " on loader " + loader);
        } catch (Throwable x) {
            LOGGER.log(Level.WARNING, "failed stubbing " +  className + " on loader " + loader + " " + x.getMessage(), x);
            return bytes;
        }

		return target.toByteArray();
	}
}
