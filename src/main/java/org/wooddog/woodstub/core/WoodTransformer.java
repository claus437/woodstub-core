package org.wooddog.woodstub.core;

import org.wooddog.woodstub.core.instrumentation.StubCodeGenerator;
import org.wooddog.woodstub.core.runtime.Stub;

import java.io.*;
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
    PrintWriter out;
    File dump = new File("c:\\woodstub-dump");

	public WoodTransformer() {
		super();

        try {
            out = new PrintWriter(new FileOutputStream(new File("c:\\woodstub.log"), true));
            out.println("1");
        } catch (IOException x) {
            System.out.println("failed creating logger");
        }

    }


	public byte[] transform(ClassLoader loader, String className, Class redefiningClass, ProtectionDomain domain, byte[] bytes) throws IllegalClassFormatException {
        StubCodeGenerator stubGenerator;
        ByteArrayInputStream source;
        ByteArrayOutputStream target;

        source = new ByteArrayInputStream(bytes);
        target = new ByteArrayOutputStream();

        if (loader.getResourceAsStream("org/wooddog/woodstub/core/WoodStub.class") != null) {
            try {
                write(className, bytes);
                stubGenerator = new StubCodeGenerator();
                stubGenerator.stubClass(source, target);
                write(className + "_WOODSTUBBED", target.toByteArray());
                out.println("STUB: " + className + " " + loader + " "  + redefiningClass);
            } catch (Throwable x) {
                out.println("FAIL: " + className + " " + loader + " "  + redefiningClass);
                return bytes;
            }
        } else {
            out.println("SKIP: " + className + " " + loader + " "  + redefiningClass);
            return bytes;
        }
        out.flush();

		return target.toByteArray();
	}

    @Override
    protected void finalize() throws Throwable {
        out.close();

        super.finalize();
    }

    private void write(String name, byte[] bytes) throws IOException {
        OutputStream out;

        out = null;

        try {
            out = new BufferedOutputStream(new FileOutputStream(new File(dump, name + ".class")));
            out.write(bytes);
        } finally {
            try {
                out.close();
            } catch (IOException x) {
            }
        }
    }
}
