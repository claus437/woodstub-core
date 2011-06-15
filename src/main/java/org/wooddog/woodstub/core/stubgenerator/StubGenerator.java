package org.wooddog.woodstub.core.stubgenerator;

import org.wooddog.woodstub.core.asm.Compiler;
import org.wooddog.woodstub.core.instrumentation.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 02-06-11
 * Time: 14:38
 * To change this template use File | Settings | File Templates.
 */
public class StubGenerator {
    private ConstantPool pool;
    private String className;

    public StubGenerator(ConstantPool pool, String className) {
        this.pool = pool;
        this.className = className;
    }

    public byte[] stub(FieldInfo method) throws IOException {
        NativeType type;
        AttributeCode code;
        Compiler compiler;
        String methodName;
        String signature;

        methodName = pool.getUtf8Value(method.getNameIndex());
        signature = pool.getUtf8Value(method.getDescriptorIndex());

        type = NativeType.getNativeType(signature);

        compiler = new Compiler(pool);
        compiler.add("invokestatic", "org/wooddog/woodstub/core/WoodStub#isRunning()Z");
        compiler.add("ifeq", "END");

        compiler.add("invokestatic", "org/wooddog/woodstub/core/WoodStub#pause()V");
        compiler.add("invokestatic", "org/wooddog/woodstub/core/WoodStub#getStubFactory()Lorg/wooddog/woodstub/core/runtime/StubFactory;");
        compiler.add("aload", 0);
        compiler.add("ldc", className + "#" + methodName + signature);
        compiler.add("invokeinterface",  "org/wooddog/woodstub/core/runtime/StubFactory#createStub(Ljava/lang/Object;Ljava/lang/String;)Lorg/wooddog/woodstub/core/runtime/Stub;", 3 , 0);
        compiler.add("astore", 1);
        compiler.add("aload", 1);
        compiler.add("ifnull", "RESUME_TO_ORIGINAL_CODE_BLOCK");

        compiler.add("aload", 1);
        compiler.add("iconst", 0);
        compiler.add("anewarray", "java/lang/String");
        compiler.add("iconst", 0);
        compiler.add("anewarray", "java/lang/Object");
        compiler.add("invokeinterface", "org/wooddog/woodstub/core/runtime/Stub#setParameters([Ljava/lang/String;[Ljava/lang/Object;)V", 3, 0);

        compiler.add("aload", 1);
        compiler.add("invokeinterface", "org/wooddog/woodstub/core/runtime/Stub#execute()V", 1, 0);

        addReturnBlock(compiler, type);

        compiler.setLabel("RESUME_TO_ORIGINAL_CODE_BLOCK");
        compiler.add("invokestatic", "org/wooddog/woodstub/core/WoodStub#resume()V");
        compiler.add("goto", "END");

        compiler.add("astore", 2 + type.size());
        compiler.add("invokestatic", "org/wooddog/woodstub/core/WoodStub#resume()V");
        compiler.add("aload", 2 + type.size());
        compiler.add("athrow");

        code = (AttributeCode) method.getAttributes("Code").get(0);

        setMaxSize(code, type);

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        stream.write(compiler.compile());
        stream.write(code.getCode());

        return stream.toByteArray();
    }

    private void setMaxSize(AttributeCode code, NativeType type) {
        int locals;

        locals = 3 + type.size();

        if (code.getMaxLocals() < locals) {
            code.setMaxLocals(locals);
        }

        if (code.getMaxStack() < 3) {
            code.setMaxStack(3);
        }
    }


    private void addReturnBlock(Compiler compiler, NativeType type) {
        if (type.isVoid()) {
            addReturnVoidBlock(compiler, type);
        } else {
            addReturnValueBlock(compiler, type);
        }
    }

    private void addReturnVoidBlock(Compiler compile, NativeType type) {

    }



    private void addReturnValueBlock(Compiler compiler, NativeType type) {
        compiler.add("aload", 1);
        compiler.add("invokeinterface", "org/wooddog/woodstub/core/runtime/Stub#getResult()Ljava/lang/Object;", 1, 0);

        if (! "java/lang/Object".equals(type.getType())) {
            compiler.add("checkcast", type.getType());
        }

        if (type.isPrimitive()) {
            compiler.add("invokevirtual", type.getParseMethod());
        }

        if (type.isArray()) {
            compiler.add("checkcast", type.getType());
        }

        compiler.add(type.getStoreOperation(), 2);
        compiler.add("invokestatic", "org/wooddog/woodstub/core/WoodStub#resume()V");
        compiler.add(type.getLoadOperation(), 2);
        compiler.add(type.getReturnOperation());
    }
}
