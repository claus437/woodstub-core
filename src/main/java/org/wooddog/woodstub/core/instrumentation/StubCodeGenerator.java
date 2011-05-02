package org.wooddog.woodstub.core.instrumentation;

import com.sun.org.apache.bcel.internal.classfile.ConstantMethodref;
import org.wooddog.woodstub.core.ClassReader;
import org.wooddog.woodstub.core.asm.Instruction;
import org.wooddog.woodstub.core.asm.SymbolicLink;
import org.wooddog.woodstub.core.asm.SymbolicLinkTable;

import java.io.*;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 27-04-11
 * Time: 17:43
 * To change this template use File | Settings | File Templates.
 */
public class StubCodeGenerator {
    private byte[] code;
    private ConstantPool pool;
    private ClassReader reader;

    private int idxMethodStubFactory;
    private int idxMethodCreateStub;
    private int idxMethodSetParameters;
    private int idxMethodExecute;
    private int idxMethodResult;
    private int idxMethodIntValue;
    private int idxClassObject;
    private int idxClassInteger;
    private int adrCodeStart;

    private int idxStringClassName;

    public byte[] stubClass(InputStream stream) throws IOException {
        ByteArrayOutputStream stubbedClass;
        List<FieldInfo> methods;

        reader = new ClassReader();
        reader.read(stream);
        pool = reader.getConstantPool();

        System.out.println("class" + reader.getIndexOfClass());

        List l = reader.getAttributes("Code");
        methods = reader.getMethods();

        setup();

        for (FieldInfo method : methods) {
            stub(method);
        }

        pool.dump();

        stubbedClass = new ByteArrayOutputStream();
        //reader.write(stubbedClass);

        return stubbedClass.toByteArray();
    }

    public void setup() {
        String className;

        className = ((ConstantUtf8Info) pool.get(((ConstantClassInfo) pool.get(reader.getIndexOfClass())).getNameIndex())).getValue();


        idxMethodStubFactory = pool.addMethodRef("org/wooddog/woodstub/core/WoodStub", "getStubFactory", "()Lorg/wooddog/woodstub/core/runtime/StubFactory;");
        idxStringClassName = pool.addString(className);
        idxMethodCreateStub = pool.addInterfaceMethodRefInfo("org/wooddog/woodstub/core/runtime/StubFactory", "createStub", "(Ljava/lang/Object;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Lorg/wooddog/woodstub/core/runtime/Stub;");
        idxMethodSetParameters = pool.addInterfaceMethodRefInfo("org/wooddog/woodstub/core/runtime/StubFactory", "setParameters", "([Ljava/lang/String;[Ljava/lang/Object;)V");
        idxMethodExecute = pool.addInterfaceMethodRefInfo("org/wooddog/woodstub/core/runtime/StubFactory", "execute", "()V");
        idxMethodResult = pool.addInterfaceMethodRefInfo("org/wooddog/woodstub/core/runtime/StubFactory", "getResult", "()Ljava/lang/Object;");
        idxMethodIntValue = pool.addMethodRef("java/lang/Integer", "intValue", "()I");
        idxClassObject = pool.addClass("java/lang/Object");
        idxClassInteger = pool.addClass("java/lang/Integer");

    }

    public void stub(FieldInfo method) {
        int idxStringName;
        int idxStringDescriptor;

        idxStringName = pool.addString(((ConstantUtf8Info) pool.get(method.getNameIndex())).getValue());
        idxStringDescriptor = pool.addString(((ConstantUtf8Info) pool.get(method.getDescriptorIndex())).getValue());

        addInstruction("invokestatic", idxMethodStubFactory);
        addInstruction("aload_0");
        addInstruction("ldc", idxStringClassName);
        addInstruction("ldc", idxStringName);
        addInstruction("ldc", idxStringDescriptor);
        addInstruction("invokeinterface", idxMethodCreateStub, 5, 0);
        addInstruction("astore_3");
        addInstruction("aload_3");
        addInstruction("ifnull", adrCodeStart);
        addInstruction("aload_3");
        addInstruction("aconst_null");
        addInstruction("iconst_2");
        addInstruction("anewarray", idxClassObject);

        addInstruction("dup");
        addInstruction("iconst_0");
        addInstruction("aload_1");
        addInstruction("aastore");

        addInstruction("dup");
        addInstruction("iconst_1");
        addInstruction("aload_2");
        addInstruction("aastore");

        addInstruction("invokeinterface", idxMethodSetParameters, 3, 0);
        addInstruction("aload_3");
        addInstruction("invokeinterface", idxMethodExecute, 1, 0);
        addInstruction("aload_3");
        addInstruction("invokeinterface", idxMethodResult, 1, 0);
        addInstruction("checkcast", idxClassInteger);
        addInstruction("invokevirtual", idxMethodIntValue);
        addInstruction("ireturn");
    }


    private void addInstruction(String instruction, Object... parameters) {
        System.out.println(instruction + " " + Arrays.asList(parameters));
    }

    public static void main(String[] args) throws IOException {
        File file;
        FileInputStream stream;
        FileOutputStream out;
        StubCodeGenerator cg;
        byte[] stubbedClass;

        file = new File("C:\\git-projects\\woodstub-core\\target\\classes\\org\\wooddog\\woodstub\\core\\Test.class");


        stream = new FileInputStream(file);

        cg = new StubCodeGenerator();
        stubbedClass = cg.stubClass(stream);
        stream.close();

        //out = new FileOutputStream(file);
        //out.write(stubbedClass);
        //out.close();
    }
}
