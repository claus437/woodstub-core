package org.wooddog.woodstub.core.instrumentation;

import com.sun.org.apache.bcel.internal.classfile.ConstantMethodref;
import org.wooddog.woodstub.core.ClassReader;
import org.wooddog.woodstub.core.asm.CodeTable;
import org.wooddog.woodstub.core.asm.Instruction;
import org.wooddog.woodstub.core.asm.SymbolicLink;
import org.wooddog.woodstub.core.asm.SymbolicLinkTable;

import java.io.*;
import java.lang.reflect.Method;
import java.util.ArrayList;
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
    private List<Instruction> instructions;
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

    public void stubClass(InputStream stream, OutputStream target) throws IOException {
        DataOutputStream out;
        ByteArrayOutputStream buffer;
        List<FieldInfo> methods;
        AttributeCode code;

        instructions = new ArrayList<Instruction>();
        reader = new ClassReader();
        reader.read(stream);
        pool = reader.getConstantPool();


        methods = reader.getMethods();

        setup();

        for (FieldInfo method : methods) {
            code = (AttributeCode) method.getAttributes("Code").get(0);

            buffer = new ByteArrayOutputStream();
            out = new DataOutputStream(buffer);

            System.out.println("nameindex: " + method.getNameIndex());

            if (method.getNameIndex() == 14) {
                stub(method);
                write(out);
                code.setMaxLocals(4);
                code.setMaxStack(6);
            }

            out.flush();
            out.close();

            buffer.write(code.getCode());
            code.setCode(buffer.toByteArray());

            instructions.clear();
        }

        pool.dump();

        reader.write(target);
    }

    public void setup() {
        String className;

        className = ((ConstantUtf8Info) pool.get(((ConstantClassInfo) pool.get(reader.getIndexOfClass())).getNameIndex())).getValue();


        idxMethodStubFactory = pool.addMethodRef("org/wooddog/woodstub/core/WoodStub", "getStubFactory", "()Lorg/wooddog/woodstub/core/runtime/StubFactory;");
        idxStringClassName = pool.addString(className);
        idxMethodCreateStub = pool.addInterfaceMethodRefInfo("org/wooddog/woodstub/core/runtime/StubFactory", "createStub", "(Ljava/lang/Object;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Lorg/wooddog/woodstub/core/runtime/Stub;");
        idxMethodSetParameters = pool.addInterfaceMethodRefInfo("org/wooddog/woodstub/core/runtime/Stub", "setParameters", "([Ljava/lang/String;[Ljava/lang/Object;)V");
        idxMethodExecute = pool.addInterfaceMethodRefInfo("org/wooddog/woodstub/core/runtime/Stub", "execute", "()V");
        idxMethodResult = pool.addInterfaceMethodRefInfo("org/wooddog/woodstub/core/runtime/Stub", "getResult", "()Ljava/lang/Object;");
        idxMethodIntValue = pool.addMethodRef("java/lang/Integer", "intValue", "()I");
        idxClassObject = pool.addClass("java/lang/Object");
        idxClassInteger = pool.addClass("java/lang/Integer");

    }

    public void stub(FieldInfo method) {
        int idxStringName;
        int idxStringDescriptor;
        int size;
        int jumpOffset;

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

        jumpOffset = calculateCodeSize();
        addInstruction("ifnull", -1);
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

        size = calculateCodeSize();
        while (size % 4 != 0) {
            addInstruction("nop");
            size ++;
        }

        instructions.get(8).setValues(new int[]{size - jumpOffset});

    }


    private void addInstruction(String name, int... parameters) {
        Instruction instruction;

        instruction = new Instruction(CodeTable.getInstructionDefinition(name));
        instruction.setValues(parameters);

        instructions.add(instruction);
    }

    private int calculateCodeSize() {
        int size;

        size = 0;

        for (Instruction instruction : instructions) {
            size += instruction.getLength();
        }

        System.out.println("size: " + size);
        return size;
    }

    private void write(DataOutputStream stream) throws IOException {
        for (Instruction instruction : instructions) {
            instruction.write(stream);
        }
    }

    public static void main(String[] args) throws IOException {
        File file;
        File targetFile;
        FileInputStream stream;
        FileOutputStream target;
        StubCodeGenerator cg;


        file = new File("C:\\git-projects\\woodstub-core\\target\\classes\\org\\wooddog\\woodstub\\core\\Test.class");
        targetFile = new File("C:\\git-projects\\woodstub-core\\target\\classes\\org\\wooddog\\woodstub\\core\\Test.class");

        target = new FileOutputStream(targetFile);
        stream = new FileInputStream(file);

        cg = new StubCodeGenerator();
        cg.stubClass(stream, target);
        stream.close();

    }

    public void dump() {
        Instruction instruction;
        int add;

        add = 0;

        for (int i = 0; i < instructions.size(); i++) {
            instruction = instructions.get(i);

            System.out.print("\n" + add + " " + instruction.getName() + " ");

            for (int j = 0; j < instruction.getValues().length; j++) {
                System.out.print(instruction.getValues()[j] + " ");
            }

            add += instruction.getLength();
        }
    }
}
