package org.wooddog.woodstub.core;

import org.wooddog.woodstub.core.instrumentation.StubCodeGenerator;

import java.io.*;
import org.junit.Test;


/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 06-05-11
 * Time: 22:56
 * To change this template use File | Settings | File Templates.
 */
public class ParameterTest {

    @Test
    public void testParameters() throws IOException {
        StubCodeGenerator gen;
        File sourceFile;
        File targetFile;
        InputStream source;
        OutputStream target;

        sourceFile = new File("target/test-classes/org/wooddog/woodstub/core/ParameterTestObject.class");
        targetFile = new File("target/test-classes/org/wooddog/woodstub/core/ParameterTestObjectTarget.class");

        source = new FileInputStream(sourceFile);
        target = new FileOutputStream(targetFile);

        gen = new StubCodeGenerator();
        gen.stubClass(source, target);

        source.close();
        target.close();

        sourceFile.delete();
        targetFile.renameTo(sourceFile);

        ParameterTestObject p = new ParameterTestObject();

        /*
        p.parameterBoolean(true);
        p.parameterByte(Byte.MAX_VALUE);
        p.parameterChar('c');
        p.parameterShort(Short.MAX_VALUE);
        p.parameterInteger(Integer.MAX_VALUE);
        p.parameterFloat(10);
        p.parameterLong(20);
        p.parameterDouble(30);
        p.parameterObject(new Object());
        //p.parameterArray(new int[]{1});
        */

        int i[][];

        i = new int[1][1];
        i[0][0] = 1;


        //p.parameter2Array(i);
        //p.all(true, Byte.MAX_VALUE);//, 'c');//, Short.MAX_VALUE); //, Integer.MAX_VALUE, 10, 20 , 30, new Object(), new Object[]{3});




    }


}
