package org.wooddog.woodstub.core;

import org.omg.CORBA.DATA_CONVERSION;
import org.wooddog.woodstub.core.instrumentation.StubCodeGenerator;

import java.io.*;
import java.nio.Buffer;

/**
 * Created by IntelliJ IDEA.
 * User: dencbr
 * Date: 24-05-11
 * Time: 15:31
 * To change this template use File | Settings | File Templates.
 */
public class IOUtil {

    public static byte[] read(File file) throws IOException {
        BufferedInputStream stream;
        byte[] data;

        stream = new BufferedInputStream(new FileInputStream(file));

        try {
            data = read(stream);
        } finally {
            close(file.getAbsolutePath(), stream);
        }

        return data;
    }

    public static byte[] read(InputStream stream) throws IOException {
        ByteArrayOutputStream out;
        byte[] data;
        int length;

        data = new byte[4096];
        out = new ByteArrayOutputStream();

        while((length = stream.read(data)) != -1) {
            out.write(data, 0, length);
        }

        return out.toByteArray();
    }

    public static void write(byte[] data, File file) throws IOException {
        BufferedOutputStream stream;

        stream = new BufferedOutputStream(new FileOutputStream(file));

        try {
            stream.write(data);
        } finally {
            close(file.getAbsolutePath(), stream);
        }
    }

    public static void write(byte[] data, OutputStream stream) throws IOException {
        stream.write(data);
    }

    public static void close(String name, Closeable resource) {
        if (resource == null) {
            return;
        }

        try {
            resource.close();
        } catch (IOException x) {
            System.err.println("unable to close " + name + ", " + x.getMessage());
        }
    }

    public static void loadStubbedClass(String classpath, String name) {
        StubCodeGenerator stubGenerator;
        File classFile;
        byte[] source;
        ByteArrayOutputStream stubbed;


        classFile = new File(classpath + "/" + name + ".class");
        stubbed = new ByteArrayOutputStream();

        try {
            source = read(classFile);
        } catch (IOException x) {
            throw new RuntimeException(x.getMessage(), x);
        }

        try {
            stubGenerator = new StubCodeGenerator();
            stubGenerator.stubClass(new ByteArrayInputStream(source), stubbed);
            write(stubbed.toByteArray(), classFile);

            Class.forName(name.replaceAll("/", "."));
        } catch (IOException x) {
            throw new RuntimeException(x);
        } catch (ClassNotFoundException x) {
            throw new RuntimeException(x);
        } finally {
            try {
                write(source, classFile);
            } catch (IOException x) {
                System.out.println("failed reestablishing original class");
            }
        }
    }
}
