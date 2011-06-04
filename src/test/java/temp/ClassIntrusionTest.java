package temp;

import java.io.*;

import com.sun.accessibility.internal.resources.accessibility;
import org.junit.Test;
import org.wooddog.woodstub.core.ClassReader;
import org.wooddog.woodstub.core.Converter;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 25-04-11
 * Time: 22:34
 * To change this template use File | Settings | File Templates.
 */
public class ClassIntrusionTest {
    private int internalError = 0;
    private int compareError = 0;
    private int ok = 0;
    private PrintStream err;

    private static FileFilter FILTER = new FileFilter() {
        public boolean accept(File pathname) {
            return pathname.getName().endsWith(".class") || pathname.isDirectory();
        }
    };


    @Test
    public void testIntrusion() throws Exception {
        err = new PrintStream(new FileOutputStream("error.log"));

        try {
            load(new File("c:\\woodstub-test\\classes"));
        } finally {
            System.out.println("in" + internalError);
            System.out.println("ce" + compareError);
            System.out.println("ok" + ok);
            err.close();
        }
    }

    @Test
    public void testSingle() throws IOException {
        File file;

        err = System.out;

        //file = new File("c:\\woodstub-test\\classes\\com\\sun\\corba\\se\\impl\\activation\\RepositoryImpl.class");
        file = new File("C:\\git-hub\\woodstub-core\\target\\classes\\org\\wooddog\\woodstub\\core\\GetBoolean.class");

        load(file);
    }


    public void load(File path) {
        File[] files;

        if (path.isDirectory()) {
            files = path.listFiles(FILTER);
            for (File file : files) {
                load(file);
            }
        } else {
            try {
                intrude(path);
                System.out.println(">" + path.getAbsolutePath() + " ok");
                ok ++;
            } catch (Throwable x) {
                err.println("failed intruding " + path.getAbsolutePath() + " " + x.getMessage());
                x.printStackTrace(err);
                internalError ++;
            }
        }
    }

    public void intrude(File path) throws IOException {
        ClassReader reader;
        ByteArrayOutputStream result;
        InputStream source;
        byte[] classA;

        reader = new ClassReader();

        source = new FileInputStream(path);
        try {
            classA = read(source);
        } catch (IOException x) {
            err.println("failed reading " + path.getAbsolutePath() + " " + x.getMessage());
            x.printStackTrace(err);
            throw x;
        } finally {
            source.close();
        }

        reader.read(new ByteArrayInputStream(classA));

        result = new ByteArrayOutputStream();
        reader.write(result);

        compare(path, classA, result.toByteArray());

    }

    private byte[] read(InputStream stream) throws IOException {
        ByteArrayOutputStream out;
        byte[] buffer;
        int lenght;

        buffer = new byte[4096];
        out = new ByteArrayOutputStream();

        while((lenght = stream.read(buffer)) != -1) {
            out.write(buffer, 0, lenght);
        }

        return out.toByteArray();
    }

    private void compare(File file, byte[] classA, byte[] classB) {
        String hexA;
        String hexB;

        if (classA.length != classB.length) {
            err.println("inconsistent size " + " " + file.getAbsolutePath() + " " + classA.length + " " + classB.length);
            System.out.println("inconsistent size " + " " + file.getAbsolutePath() + " " + classA.length + " " + classB.length);
            compareError ++;
            return;
        }

        hexA = hexDump(classA);
        hexB = hexDump(classB);

        if (! hexA.equals(hexB)) {
            err.println(file.getAbsolutePath() + " did not match");
            System.out.println(file.getAbsolutePath() + " did not match");
            compareError ++;
        }

    }


    public static String hexDump(byte[] bytes) {
        StringBuffer buffer;

        buffer = new StringBuffer();

        for (int i = 0; i < bytes.length; i++) {
            if (i % 32 == 0) {
                buffer.append("\n");
            }

            buffer.append(format(Converter.asUnsigned(bytes[i])) + " ");

        }

        return buffer.toString();
    }

    private static String format(int i) {
        String hex;

        hex = Integer.toHexString(i);
        return hex.length() == 1 ? "0" + hex : hex;
    }

}
