package org.wooddog.woodstub.core.instrumentation;

import com.sun.org.apache.bcel.internal.classfile.ConstantMethodref;
import org.wooddog.woodstub.core.ClassReader;
import org.wooddog.woodstub.core.InternalErrorException;
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

            if (!((ConstantUtf8Info) pool.get(method.getDescriptorIndex())).getValue().equals("()V")) {
                System.out.println("stubbing: \"" + ((ConstantUtf8Info) pool.get(method.getDescriptorIndex())).getValue() + "\"");
                stub(method);
                write(out);
                code.setMaxLocals(code.getMaxLocals() + 4);
                code.setMaxStack(code.getMaxStack() + 6);
            }

            out.flush();
            out.close();

            buffer.write(code.getCode());
            code.setCode(buffer.toByteArray());

            instructions.clear();

            System.out.println("ml: " + code.getMaxLocals());
            System.out.println("ms: " + code.getMaxStack());
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
        idxClassObject = pool.addClass("java/lang/Object");

    }

    public void stub(FieldInfo method) {
        int idxStringName;
        int idxStringDescriptor;
        int size;
        int jumpOffset;
        String methodDescriptor;

        methodDescriptor = ((ConstantUtf8Info) pool.get(method.getDescriptorIndex())).getValue();

        idxStringName = pool.addString(((ConstantUtf8Info) pool.get(method.getNameIndex())).getValue());
        idxStringDescriptor = pool.addString(methodDescriptor);

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

        addParameterValues(methodDescriptor);

        addInstruction("invokeinterface", idxMethodSetParameters, 3, 0);
        addInstruction("aload_3");
        addInstruction("invokeinterface", idxMethodExecute, 1, 0);
        addInstruction("aload_3");
        addInstruction("invokeinterface", idxMethodResult, 1, 0);

        addReturn(methodDescriptor.substring(methodDescriptor.lastIndexOf(")") + 1));


        size = calculateCodeSize();
        while (size % 4 != 0) {
            addInstruction("nop");
            size ++;
        }

        instructions.get(8).setValues(new int[]{size - jumpOffset});

        dump();
    }

    private void addParameterValues(String methodDescriptor) {
        CharSequence args;
        int arrayIndex;
        int lIndex;
        Instruction instruction;
        int index;


        args = methodDescriptor.subSequence(1, methodDescriptor.length() - 1);

        if (args.length() == 0) {
            addInstruction("iconst_0");
            addInstruction("anewarray", pool.addClass("java/lang/Object"));
        }

        arrayIndex = instructions.size();
        instructions.add(null);
        addInstruction("anewarray", pool.addClass("java/lang/Object"));

        lIndex = 0;

        for (index = 0; index < args.length(); index++) {

            if (args.charAt(index) == ')') {
                if (lIndex > 5) {
                    instruction = new Instruction(CodeTable.getInstructionDefinition("bipush"));
                    instruction.setValues(new int[]{lIndex});
                } else {
                    instruction = new Instruction(CodeTable.getInstructionDefinition("iconst_" + lIndex));
                }
                instructions.set(arrayIndex, instruction);
                return;
            }

            lIndex++;

            addInstruction("dup");
            if (index > 5) {
                addInstruction("bipush", index);
            } else {
                addInstruction("iconst_" + index);
            }

            switch (args.charAt(index)) {
                case 'Z':
                    addInstruction("iload", lIndex);
                    addInstruction("invokestatic", pool.addMethodRef("java/lang/Boolean", "valueOf", "(Z)Ljava/lang/Boolean;"));
                    break;

                case 'B':
                    addInstruction("iload", lIndex);
                    addInstruction("invokestatic", pool.addMethodRef("java/lang/Byte", "valueOf", "(B)Ljava/lang/Byte;"));
                    break;

                case 'C':
                    addInstruction("iload", lIndex);
                    addInstruction("invokestatic", pool.addMethodRef("java/lang/Character", "valueOf", "(C)Ljava/lang/Character;"));
                    break;

                case 'S':
                    addInstruction("iload", lIndex);
                    addInstruction("invokestatic", pool.addMethodRef("java/lang/Short", "valueOf", "(S)Ljava/lang/Short;"));
                    break;

                case 'I':
                    addInstruction("iload", lIndex);
                    addInstruction("invokestatic", pool.addMethodRef("java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;"));
                    break;

                case 'F':
                    addInstruction("fload", lIndex);
                    addInstruction("invokestatic", pool.addMethodRef("java/lang/Float", "valueOf", "(F)Ljava/lang/Float;"));
                    break;

                case 'J':
                    addInstruction("lload", lIndex);
                    addInstruction("invokestatic", pool.addMethodRef("java/lang/Long", "valueOf", "(J)Ljava/lang/Long;"));
                    lIndex ++;
                    break;

                case 'D':
                    addInstruction("dload", lIndex);
                    addInstruction("invokestatic", pool.addMethodRef("java/lang/Double", "valueOf", "(D)Ljava/lang/Double;"));
                    lIndex++;
                    break;

                case 'L':
                    while (args.charAt(index) != ';') {
                        index++;
                    }
                    addInstruction("aload", lIndex);
                    System.out.println("done reading at index " + index);

                    break;

                case '[':
                    while (args.charAt(index) == '[') {
                        index++;
                    }

                    if (args.charAt(index) == 'L') {
                        while (args.charAt(index) != ';') {
                            index++;
                        }
                    }

                    addInstruction("aload", lIndex);
                    break;

                default:
                    throw new InternalErrorException("unable to parse parameters in description " + methodDescriptor + " failed on index " + index + "/" + args.charAt(index));
            }

            addInstruction("aastore");
        }
    }

    private void addReturn(String methodDescriptor) {
        String type;

        type = methodDescriptor.substring(methodDescriptor.lastIndexOf(")") + 1);

        System.out.println("TYPE: ");

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

                case 'V':
                    addInstruction("return");
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
        }

        addInstruction("checkcast", pool.addClass(type.substring(1, type.length() - 1)));
        addInstruction("areturn");
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
}
