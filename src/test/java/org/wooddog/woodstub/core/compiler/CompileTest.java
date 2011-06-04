package org.wooddog.woodstub.core.compiler;

import org.junit.Test;
import org.wooddog.woodstub.core.asm.*;
import org.wooddog.woodstub.core.asm.Compiler;
import org.wooddog.woodstub.core.instrumentation.ConstantPool;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 03-06-11
 * Time: 22:30
 * To change this template use File | Settings | File Templates.
 */
public class CompileTest {

    @Test
    public void testCompile() {
        Compiler compiler;


        compiler = new Compiler(new ConstantPool());
        compiler.add("ldc", "org");

    }
}
