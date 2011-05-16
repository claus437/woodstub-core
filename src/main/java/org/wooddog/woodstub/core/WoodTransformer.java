package org.wooddog.woodstub.core;

import org.wooddog.woodstub.core.instrumentation.StubCodeGenerator;

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
    private static final File DUMP = new File("c:\\woodstub-DUMP");

	public WoodTransformer() {
		super();

        try {
            File file = new File("woodstub.log");
            out = new PrintWriter(new FileOutputStream(file));
            System.out.println("created logger " + file.getCanonicalPath());

        } catch (IOException x) {
            System.out.println("failed creating logger " + x.getMessage());
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
                stubGenerator.stubClass(className, source, target);
                write(className + "_WOODSTUBBED", target.toByteArray());
                out.println("STUB: " + className + " " + loader + " " + redefiningClass);
                out.flush();
            } catch (Throwable x) {
                out.println("FAIL: " + className + " " + loader + " "  + redefiningClass + " " + x.getMessage());
                x.printStackTrace(out);
                out.flush();
                return bytes;
            }
        } else {
            out.println("SKIP: " + className + " " + loader + " "  + redefiningClass);
            out.flush();
            return bytes;
        }

		return target.toByteArray();
	}

    @Override
    protected void finalize() throws Throwable {
        out.close();

        super.finalize();
    }

    private void write(String name, byte[] bytes) throws IOException {
        OutputStream out;
        File file;

        out = null;

        file = new File(DUMP, name + ".class");
        file.getParentFile().mkdirs();

        try {
            out = new BufferedOutputStream(new FileOutputStream(file));
            out.write(bytes);
        } catch (IOException x) {
            this.out.println("FAILED WRITING " + file.toString() + " " + x.getMessage());
        } finally {
            close(out);
        }
    }

    public static void write(String text) {
        BufferedWriter out;

        out = null;

        try {
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(DUMP, "decompile.log"), true)));
            out.write(text);
        } catch (IOException x) {
            System.out.println("error closing DUMP file");
        } finally {
            close(out);
        }
    }

    private static void close(Closeable resource) {
        if (resource != null) {
            try {
                resource.close();
            } catch (IOException x) {
                System.out.println("FAILED CLOSING " + x.getMessage());
            }
        }
    }
}
