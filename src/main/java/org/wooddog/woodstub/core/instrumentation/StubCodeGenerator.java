package org.wooddog.woodstub.core.instrumentation;

import com.sun.org.apache.bcel.internal.classfile.ConstantMethodref;
import org.wooddog.woodstub.core.ClassReader;
import org.wooddog.woodstub.core.InternalErrorException;
import org.wooddog.woodstub.core.ToolBox;
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
    private int idxClassObject;
    private int adrCodeStart;

    private int idxStringClassName;

    public void stubClass(InputStream stream, OutputStream target) throws IOException {
        DataOutputStream out;
        ByteArrayOutputStream buffer;
        List<FieldInfo> methods;
        AttributeCode code;
        int mem[];

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

            if (!((ConstantUtf8Info) pool.get(method.getNameIndex())).getValue().equals("<init>")) {
                System.out.println("stubbing: \"" + ((ConstantUtf8Info) pool.get(method.getNameIndex())) + " " + ((ConstantUtf8Info) pool.get(method.getDescriptorIndex())).getValue() + "\"");
                stub(code, method);
                write(out);
            } else {
                System.out.println("skipping " + ((ConstantUtf8Info) pool.get(method.getNameIndex())) + " " + ((ConstantUtf8Info) pool.get(method.getDescriptorIndex())).getValue());
            }

            out.flush();
            out.close();

            buffer.write(code.getCode());
            code.setCode(buffer.toByteArray());

            instructions.clear();
        }
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
        idxClassObject = pool.addClass("java/lang/Object");

    }

    public void stub(AttributeCode code, FieldInfo method) {
        int idxStringName;
        int idxStringDescriptor;
        int size;
        int jumpOffset;
        String methodDescriptor;
        char[] parameterTypes;
        int parameterRegisterSize;


        methodDescriptor = ((ConstantUtf8Info) pool.get(method.getDescriptorIndex())).getValue();

        parameterTypes = ToolBox.getParameterBaseTypes(methodDescriptor);
        parameterRegisterSize = getRegisterSize(parameterTypes);

        if (code.getMaxStack() < 5) {
            code.setMaxStack(5);
        }

        if (code.getMaxLocals() < 3 + parameterRegisterSize) {
            code.setMaxLocals(parameterRegisterSize + 3);
        }

        idxStringName = pool.addString(((ConstantUtf8Info) pool.get(method.getNameIndex())).getValue());
        idxStringDescriptor = pool.addString(methodDescriptor);

        addInstruction("invokestatic", idxMethodStubFactory);
        addInstruction("aload_0");
        addInstruction("ldc", idxStringClassName);
        addInstruction("ldc", idxStringName);
        addInstruction("ldc", idxStringDescriptor);
        addInstruction("invokeinterface", idxMethodCreateStub, 5, 0);
        addInstruction("astore", parameterRegisterSize + 1);

        addInstruction("aload", parameterRegisterSize + 1);
        jumpOffset = calculateCodeSize();
        addInstruction("ifnull", -1);

        addParameterValueArray(parameterTypes, parameterRegisterSize + 2, methodDescriptor);

        addInstruction("aload", parameterRegisterSize + 1);
        addInstruction("aconst_null");
        addInstruction("aload", parameterRegisterSize + 2);
        addInstruction("invokeinterface", idxMethodSetParameters, 3, 0);

        addInstruction("aload", parameterRegisterSize + 1);
        addInstruction("invokeinterface", idxMethodExecute, 1, 0);

        addInstruction("aload", parameterRegisterSize + 1);
        addReturn(methodDescriptor.substring(methodDescriptor.lastIndexOf(")") + 1));


        size = calculateCodeSize();
        while (size % 4 != 0) {
            addInstruction("nop");
            size ++;
        }

        instructions.get(8).setValues(new int[]{size - jumpOffset});

        //dump();
    }


    private void addParameterValueArray(char[] parameterTypes, int address, String descriptor) {
        int arrayAddress;
        int stackAddress;


        if (parameterTypes.length > 5) {
            addInstruction("bipush", parameterTypes.length);
        } else {
            addInstruction("iconst_" + parameterTypes.length);
        }

        arrayAddress = address;
        addInstruction("anewarray", pool.addClass("java/lang/Object"));
        addInstruction("astore", arrayAddress);

        stackAddress = 1;

        for (int i = 0; i < parameterTypes.length; i++) {
            addInstruction("aload", arrayAddress);

            if (i < 5) {
                addInstruction("iconst_" + i);
            } else {
                addInstruction("bipush", i);
            }

            switch (parameterTypes[i]) {
                case 'Z':
                    addInstruction("iload", stackAddress);
                    addInstruction("invokestatic", pool.addMethodRef("java/lang/Boolean", "valueOf", "(Z)Ljava/lang/Boolean;"));
                    break;

                case 'B':
                    addInstruction("iload", stackAddress);
                    addInstruction("invokestatic", pool.addMethodRef("java/lang/Byte", "valueOf", "(B)Ljava/lang/Byte;"));
                    break;

                case 'C':
                    addInstruction("iload", stackAddress);
                    addInstruction("invokestatic", pool.addMethodRef("java/lang/Character", "valueOf", "(C)Ljava/lang/Character;"));
                    break;

                case 'S':
                    addInstruction("iload", stackAddress);
                    addInstruction("invokestatic", pool.addMethodRef("java/lang/Short", "valueOf", "(S)Ljava/lang/Short;"));
                    break;

                case 'I':
                    addInstruction("iload", stackAddress);
                    addInstruction("invokestatic", pool.addMethodRef("java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;"));
                    break;

                case 'F':
                    addInstruction("fload", stackAddress);
                    addInstruction("invokestatic", pool.addMethodRef("java/lang/Float", "valueOf", "(F)Ljava/lang/Float;"));
                    break;

                case 'J':
                    addInstruction("lload", stackAddress);
                    addInstruction("invokestatic", pool.addMethodRef("java/lang/Long", "valueOf", "(J)Ljava/lang/Long;"));
                    stackAddress++;
                    break;

                case 'D':
                    addInstruction("dload", stackAddress);
                    addInstruction("invokestatic", pool.addMethodRef("java/lang/Double", "valueOf", "(D)Ljava/lang/Double;"));
                    stackAddress++;
                    break;

                case 'L':
                    addInstruction("aload", stackAddress);
                    break;

                default:
                    throw new InternalErrorException("unknown parameter type " + parameterTypes[i] + " " + descriptor + " " + toString(parameterTypes));
            }

            stackAddress ++;
            addInstruction("aastore");
        }


    }


    private void addReturn(String methodDescriptor) {
        String type;

        type = methodDescriptor.substring(methodDescriptor.lastIndexOf(")") + 1);

        if (type.charAt(0) == 'V') {
            addInstruction("pop");
            addInstruction("return");

            return;
        }

        addInstruction("invokeinterface", idxMethodResult, 1, 0);

        if (type.length() == 1) {
            switch (type.charAt(0)) {
                case 'Z':
                    addInstruction("checkcast", pool.addClass("java/lang/Boolean"));
                    addInstruction("invokevirtual", pool.addMethodRef("java/lang/Boolean", "booleanValue", "()Z"));
                    addInstruction("ireturn");
                    break;

                case 'B':
                    addInstruction("checkcast", pool.addClass("java/lang/Byte"));
                    addInstruction("invokevirtual", pool.addMethodRef("java/lang/Byte", "byteValue", "()B"));
                    addInstruction("ireturn");
                    break;

                case 'C':
                    addInstruction("checkcast", pool.addClass("java/lang/Character"));
                    addInstruction("invokevirtual", pool.addMethodRef("java/lang/Character", "charValue", "()C"));
                    addInstruction("ireturn");
                    break;

                case 'S':
                    addInstruction("checkcast", pool.addClass("java/lang/Short"));
                    addInstruction("invokevirtual", pool.addMethodRef("java/lang/Short", "shortValue", "()S"));
                    addInstruction("ireturn");
                    break;

                case 'I':
                    addInstruction("checkcast", pool.addClass("java/lang/Integer"));
                    addInstruction("invokevirtual", pool.addMethodRef("java/lang/Integer", "intValue", "()I"));
                    addInstruction("ireturn");
                    break;

                case 'F':
                    addInstruction("checkcast", pool.addClass("java/lang/Float"));
                    addInstruction("invokevirtual", pool.addMethodRef("java/lang/Float", "floatValue", "()F"));
                    addInstruction("freturn");
                    break;

                case 'D':
                    addInstruction("checkcast", pool.addClass("java/lang/Double"));
                    addInstruction("invokevirtual", pool.addMethodRef("java/lang/Double", "doubleValue", "()D"));
                    addInstruction("dreturn");
                    break;

                 case 'J':
                    addInstruction("checkcast", pool.addClass("java/lang/Long"));
                    addInstruction("invokevirtual", pool.addMethodRef("java/lang/Long", "longValue", "()J"));
                    addInstruction("lreturn");
                    break;

                default:
                    throw new InternalErrorException("unable to generate return code for method " + methodDescriptor);
            }

            return;
        }

        if (type.startsWith("[")) {
            addInstruction("checkcast", pool.addClass(type));
            addInstruction("checkcast", pool.addClass(type));
            addInstruction("areturn");
            return;
        }

        addInstruction("checkcast", pool.addClass(type.substring(1, type.length() - 1)));
        addInstruction("areturn");
    }

    private void addInstruction(String name, int... parameters) {
        Instruction instruction;

        if ("aload".equals(name)) {
            if (parameters[0] < 4) {
                name = "aload_" + parameters[0];
                parameters = new int[0];
            }
        }

        if ("astore".equals(name)) {
            if (parameters[0] < 4) {
                name = "astore_" + parameters[0];
                parameters = new int[0];
            }
        }

        if ("iload".equals(name)) {
            if (parameters[0] < 4) {
                name = "iload_" + parameters[0];
                parameters = new int[0];
            }
        }

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
        targetFile = new File("C:\\git-projects\\woodstub-core\\target\\classes\\org\\wooddog\\woodstub\\core\\Test2.class");


        stream = new FileInputStream(file);
        cg = new StubCodeGenerator();

        target = new FileOutputStream(targetFile);
        cg.stubClass(stream, target);
        stream.close();
        target.close();

    }

    public void dump() {
        Instruction instruction;
        int add;

        add = 0;

        System.out.println();
        for (int i = 0; i < instructions.size(); i++) {
            instruction = instructions.get(i);

            System.out.print("\n" + add + " " + instruction.getName() + " ");

            for (int j = 0; j < instruction.getValues().length; j++) {
                System.out.print(instruction.getValues()[j] + " ");
            }

            add += instruction.getLength();
        }
    }



    public int getRegisterSize(char[] types) {
        int size;

        size = 0;

        for (char type : types) {
            size += type == 'J' || type == 'D' ? 2 : 1;
        }

        return size;
    }

    private String toString(char[] values) {
        String s = "";

        for (Object i : values) {
            s += i + ",";
        }

        return s;
    }

}
