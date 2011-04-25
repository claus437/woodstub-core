package org.wooddog.woodstub.core;

import java.io.*;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 22-04-11
 * Time: 18:11
 * To change this template use File | Settings | File Templates.
 */
public class ShowFile {
    private File file;

    public void show() throws Exception {
        InputStream source;
        byte[] data;

        source = getClass().getClassLoader().getResourceAsStream("org/wooddog/woodstub/core/Test.class");

        readData(source);

        if (true) {
            return;
        }
        System.out.print("\nmagic: ");
        data = read(source, 4);
        for (byte b : data) {
            System.out.print(Integer.toHexString(b));
        }

        System.out.print("\nmin version: ");
        data = read(source, 2);
        for (byte b : data) {
            System.out.print((int)b);
        }

        System.out.print("\nmaj version: ");
        data = read(source, 2);
        for (byte b : data) {
            System.out.print((int)b);
        }

        System.out.print("\nconstant pool: ");
        data = read(source, 2);
        System.out.print((int) data[0] + " " + (int) data[1]);

        //readConstantPool(source, 50);

        data = read(source, 100);

        System.out.println("----\n");

        for (int  i = 0; i < data.length; i++) {
            if (data[i] < 20) {
                System.out.print(format("-\\") + " ");
            } else {
                System.out.print(format(Character.toString((char) data[i])) + " ");
            }
        }
        System.out.println("");

        for (int  i = 0; i < data.length; i++) {
            System.out.print(format(Integer.toString(data[i])) + " ");
        }


    }


    public void readData(InputStream in) throws Exception {
        DataInputStream data;
        int i;


        data = new DataInputStream(in);

        System.out.println("magic: " + Integer.toHexString(data.readInt()));
        System.out.println("major: " + data.readShort());
        System.out.println("minor: " + data.readShort());
        i = data.readShort();
        System.out.println("constant pool size: " + i);

        System.out.println("01 tag: " + data.readByte() + " " + data.readInt());
        System.out.println("02 tag: " + data.readByte() + " " + data.readShort());
        System.out.println("03 tag: " + data.readByte() + " " + data.readShort());

        System.out.print("04 tag: " + data.readByte());
        read1(data);

        System.out.print("05 tag: " + data.readByte());
        read1(data);

        System.out.print("06 tag: " + data.readByte());
        read1(data);

        System.out.println("07 tag: " + data.readByte() + " " + data.readInt());

        System.out.print("08 tag: " + data.readByte());
        read1(data);

        System.out.print("09 tag: " + data.readByte());
        read1(data);

        System.out.print("10 tag: " + data.readByte());
        read1(data);

        System.out.print("11 tag: " + data.readByte());
        read1(data);

        System.out.print("12 tag: " + data.readByte());
        read1(data);

        System.out.print("13 tag: " + data.readByte());
        read1(data);

        System.out.print("14 tag: " + data.readByte());
        read1(data);

        System.out.print("15 tag: " + data.readByte());
        read1(data);

        System.out.print("16 tag: " + data.readByte());
        read1(data);

        System.out.println("17 tag: " + data.readByte() + " " + data.readShort() + " " + data.readShort());

        System.out.print("18 tag: " + data.readByte());
        read1(data);

        System.out.print("19 tag: " + data.readByte());
        read1(data);

        System.out.println("access: " + data.readShort());

        System.out.println("this class: " + data.readShort());

        System.out.println("super: " + data.readShort());
        System.out.println("interface: " + data.readShort());
        System.out.println("field count: " + data.readShort());
        System.out.println("field access: " + data.readShort());
        System.out.println("name index: " + data.readShort());
        System.out.println("descriptor index: " + data.readShort());
        System.out.println("attribute count: " + data.readShort());
        System.out.println("attribute index: " + data.readShort());
        System.out.println("attribute length: " + data.readInt());
        System.out.println("attribute data: " + data.readShort());

        System.out.println("method count: " + data.readShort());
        System.out.println("method access: " + data.readShort());
        System.out.println("method name index: " + data.readShort());
        System.out.println("method descriptor index: " + data.readShort());
        System.out.println("method attribute count: " + data.readShort());
        System.out.println("method attribute index: " + data.readShort());
        System.out.println("method attribute length: " + data.readInt());
        for (int j = 0; j < 47; j++) {
            data.readByte();
        }
        System.out.println("method attribute data: ");

        while ((i = data.read()) != -1) {
            System.out.print(i + " ");
        }
    }

    public void read1(DataInputStream data) throws IOException  {
        short i;

        //i = data.readShort();
        //System.out.print(" length: " + i + " ");
        //for (int j = 0; j < i; j ++) {
            System.out.print(" " + data.readUTF());
        //}
        System.out.println("");
    }


    public byte[] read(InputStream source, int length) throws IOException {
        byte[] data;

        data = new byte[length];
        for (int i = 0; i < length; i++) {
            data[i] = (byte) source.read();
        }

        return data;
    }

    public void readConstantPool(InputStream source, int length) throws IOException {
        byte b;
        int read;

        read = 0;

        b = (byte) source.read();

        while (read < length) {

            if (b != 0) {
                System.out.print("\n#" + read + " Type: " + b);
                read ++;
            } else {
                System.out.print(" " + 0);
            }

            while ((b = (byte) source.read()) > 13) {
                System.out.print((char) b);
            }


        }
    }


    private void dump2(byte[] data) {
        for (int i = 0; i < data.length; i++) {
            if (data[i] == 1 && data[i + 1] == 0) {
                System.out.println("\n");
            } else {
                System.out.print((char) data[i]);
            }
        }
    }

    private void dump(byte[] data) {
        char c;

        for (int i = 0; i < data.length; i++) {
            c = (char) data[i];

            if ( c < 20) {
                c = '?';
            }

            System.out.print(c);

            if (i % 40 == 0 && i > 39) {
                for (int j = i -40; j < i; j++) {
                    System.out.print(" " + format((int) data[j]));
                }

                System.out.println();
            }
        }
    }

    public static boolean raiseEvent() {
        return false;
    }

    private static String format(int i) {
        String hex;

        hex = Integer.toHexString(i);
        return hex.length() == 1 ? "0" + hex : hex;
    }

    private String format(String str) {
        StringBuffer b;

        b = new StringBuffer(str);
        while(b.length() < 4) {
            b.insert(0, " ");
        }
        return b.toString();
    }


    private byte[] read(InputStream source) throws IOException {
        int length;
        byte[] data;
        ByteArrayOutputStream stream;

        data = new byte[4096];
        stream = new ByteArrayOutputStream();

        while ((length = source.read(data)) != -1) {
            stream.write(data, 0, length);
        }

        return stream.toByteArray();
    }

    public static void main(String[] args) throws Exception {
        ClassReader reader;
        ByteArrayOutputStream out;

        out = new ByteArrayOutputStream();

        reader = new ClassReader();
        reader.read(ShowFile.class.getClassLoader().getResourceAsStream("org/wooddog/woodstub/core/Test.class"));
        System.out.println(reader.toString());
        reader.write(out);

        compare(out.toByteArray());
    }

    public static void compare(byte[] result) throws IOException {
        byte[] source;
        int index;

        index = 0;
        source = read();
        System.out.println(source.length + " " + result.length);

        System.out.println("---- SOURCE");
        System.out.println(hexDump(source));


        System.out.println("\n---- TRANSF");
        System.out.println(hexDump(result));

        System.out.println("equals " + hexDump(source).equals(hexDump(result)));

    }

    public static byte[] read() throws IOException {
        InputStream in;
        int length;
        byte[] buffer;
        ByteArrayOutputStream result;

        result = new ByteArrayOutputStream();
        buffer = new byte[4096];
        in = ShowFile.class.getClassLoader().getResourceAsStream("org/wooddog/woodstub/core/Test.class");

        while((length = in.read(buffer)) != -1) {
            result.write(buffer, 0, length);
        }

        return result.toByteArray();

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
}
