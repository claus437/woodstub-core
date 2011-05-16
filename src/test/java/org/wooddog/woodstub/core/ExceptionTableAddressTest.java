package org.wooddog.woodstub.core;

import org.wooddog.woodstub.core.instrumentation.StubCodeGenerator;

import java.io.*;

import org.junit.Test;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 13-05-11
 * Time: 19:27
 * To change this template use File | Settings | File Templates.
 */
public class ExceptionTableAddressTest {

    public void throwing(int i) throws IOException, FileNotFoundException {
        if (i == 10) {
            throw new FileNotFoundException("fnf");
        }

        if (i == 20) {
            throw new FileNotFoundException("io");
        }

        try {
            new FileOutputStream("tester");
        } catch (IOException x) {
            x.printStackTrace();
        } finally {
            System.out.println("hi");
        }

        try {
            new FileOutputStream("tester");
        } catch (FileNotFoundException x) {
            x.printStackTrace();
        } finally {
            System.out.println("hi");
        }

    }

    @Test
    public void testExceptionTable() throws Exception {

        InputStream source;
        ByteArrayOutputStream target;
        StubCodeGenerator stubber;

        target = new ByteArrayOutputStream();

        stubber = new StubCodeGenerator();
        stubber.stubClass(getClass().getClassLoader().getResourceAsStream("org/wooddog/woodstub/core/ExceptionTableAddressTest.class"), target);
    }
}