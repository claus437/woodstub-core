package org.wooddog.woodstub.core;

import java.io.*;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 22-05-11
 * Time: 14:12
 * To change this template use File | Settings | File Templates.
 */
public class MyLogger {
    PrintWriter out;
    public static final File DUMP_FOLDER = new File("c:\\woodstub-dump");

    public static void init() {
        DUMP_FOLDER.mkdirs();
    }

    public static void write(String name, byte[] bytes) throws IOException {
        OutputStream out;
        File file;

        out = null;

        file = new File(DUMP_FOLDER, name + ".class");
        file.getParentFile().mkdirs();

        try {
            out = new BufferedOutputStream(new FileOutputStream(file));
            out.write(bytes);
        } finally {
            close(out);
        }
    }


    public static void write(String text) {
        BufferedWriter out;
        File file;

        out = null;
        file = new File(DUMP_FOLDER, "decompile.log");
        file.getParentFile().mkdirs();

        try {
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true)));
            out.write(text);
        } catch (IOException x) {
            System.out.println("error closing DUMP_FOLDER file" + x.getMessage());
        } finally {
            close(out);
        }
    }

    public static void append(String name, String text) {
        BufferedWriter out;
        File file;

        out = null;
        file = new File(DUMP_FOLDER, name);
        file.getParentFile().mkdirs();

        try {
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true)));
            out.write(text);
        } catch (IOException x) {
            System.out.println("error closing DUMP_FOLDER file" + x.getMessage());
        } finally {
            close(out);
        }
    }

    public static void append(String name, Throwable t) {
        PrintWriter out;
        File file;

        out = null;
        file = new File(DUMP_FOLDER, name);
        file.getParentFile().mkdirs();

        try {
            out = new PrintWriter(new FileOutputStream(file, true));
            t.printStackTrace(out);
        } catch (IOException x) {
            System.out.println("error closing DUMP_FOLDER file " + x.getMessage());
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

    public static void write(String fileName, String text) {
        BufferedWriter out;
        File file;

        out = null;
        file = new File(DUMP_FOLDER, fileName);
        file.getParentFile().mkdirs();

        try {
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true)));
            out.write(text);
        } catch (IOException x) {
            System.out.println("error closing DUMP_FOLDER file " + x.getMessage());
        } finally {
            close(out);
        }
    }

}
