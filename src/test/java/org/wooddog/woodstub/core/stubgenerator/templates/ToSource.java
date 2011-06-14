package org.wooddog.woodstub.core.stubgenerator.templates;

import org.wooddog.woodstub.core.ClassReader;
import org.wooddog.woodstub.core.IOUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 14-06-11
 * Time: 13:48
 * To change this template use File | Settings | File Templates.
 */
public class ToSource {
    public void toByteCode() throws IOException {
        ClassReader reader;
        ClassLoader loader;
        InputStream stream;
        String className;

        className = getClass().getCanonicalName().replaceAll("\\.", "/");

        loader = getClass().getClassLoader();
        stream = loader.getResourceAsStream(className + ".class");

        reader = new ClassReader();
        reader.read(stream);

        IOUtil.write(reader.getSource().getBytes(), new File("src/test/resources/org/wooddog/woodstub/core/stubgenerator/" + getClass().getSimpleName() + ".jbc"));
        System.out.println(reader.getSource());
    }




}
