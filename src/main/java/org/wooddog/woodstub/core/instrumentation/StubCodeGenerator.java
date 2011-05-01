package org.wooddog.woodstub.core.instrumentation;

import org.wooddog.woodstub.core.ClassReader;
import org.wooddog.woodstub.core.asm.Instruction;
import org.wooddog.woodstub.core.asm.SymbolicLink;
import org.wooddog.woodstub.core.asm.SymbolicLinkTable;

import java.io.*;
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
    private ConstantPool constantPool;
    private int classNameIndex;

    public byte[] stubClass(InputStream stream) throws IOException {
        ByteArrayOutputStream stubbedClass;
        List<AttributeCode> blocks;
        ClassReader reader;

        reader = new ClassReader();
        reader.read(stream);

        List l = reader.getAttributes("Code");
        blocks = (List<AttributeCode>) reader.getAttributes("Code");

        for (AttributeCode block : blocks) {
            stub(block);
        }

        stubbedClass = new ByteArrayOutputStream();
        reader.write(stubbedClass);

        return stubbedClass.toByteArray();
    }

    public void stub(AttributeCode block) {
        System.out.println(block.toString());
    }

    public void generate(ConstantMethodRefInfo methodRef) {
        SymbolicLinkTable symTable;
        int methodNameIndex;
        int address;
        ConstantNameAndTypeInfo nameAndTypeInfo;

        nameAndTypeInfo = (ConstantNameAndTypeInfo) constantPool.get(methodRef.getNameAndTypeIndex());

        symTable = new SymbolicLinkTable();
        // created once
        SymbolicLink METHOD_STUB_FACTORY = new SymbolicLink("METHOD_STUB_FACTORY", -1);
        SymbolicLink INTERFACE_METHOD_REF_CREATE_STUB = new SymbolicLink("INTERFACE_CREATE_STUB", -1);
        SymbolicLink INTERFACE_METHOD_REF_SET_PARAMETERS = new SymbolicLink("INTERFACE_SET_PARAMETERS", -1);
        SymbolicLink INTERFACE_METHOD_REF_EXECUTE = new SymbolicLink("INTERFACE_EXECUTE", -1);
        SymbolicLink INTERFACE_METHOD_REF_BEHAVIOR = new SymbolicLink("INTERFACE_BEHAVIOR", -1);
        SymbolicLink INTERFACE_METHOD_REF_RESULT = new SymbolicLink("INTERFACE_RESULT", -1);

        // present
        SymbolicLink STRING_INFO_CLASS_NAME = new SymbolicLink("STRING_CLASS_NAME", -1);
        SymbolicLink STRING_INFO_METHOD_NAME = new SymbolicLink("STRING_METHOD_NAME", -1);
        SymbolicLink STRING_INFO_METHOD_DESCRIPTION = new SymbolicLink("STRING_METHOD_DESCRIPTION", -1);

        // might not be present
        SymbolicLink CLASS_INFO_STRING = new SymbolicLink("CLASS_STRING", -1);
        SymbolicLink CLASS_INFO_OBJECT = new SymbolicLink("CLASS_OBJECT", -1);
        SymbolicLink CLASS_INFO_INTEGER = new SymbolicLink("CLASS_INTEGER", -1);
        SymbolicLink CLASS_INFO_THROWABLE = new SymbolicLink("CLASS_THROWABLE", -1);

        SymbolicLink METHOD_REF_INFO_INT_VALUE = new SymbolicLink("INVOKE_INT_VALUE", -1);


        SymbolicLink LABEL_CODE_ADDRESS = new SymbolicLink("LABEL_CODE_ADDRESS", -1);
        SymbolicLink LABEL_RETURN_ADDRESS = new SymbolicLink("LABEL_RETURN_ADDRESS", -1);
        SymbolicLink LABEL_THROW_ADDRESS = new SymbolicLink("LABEL_THROW_ADDRESS", -1);


        SymbolicLink STRING_INFO_PARAMETER_NAME_0 = new SymbolicLink("STRING_PARAMETER_0", -1);



        addInstruction("INVOKESTATIC", METHOD_STUB_FACTORY); // "org/wooddog/woodstub/core/WoodStub.getStubFactory ()Lorg/wooddog/woodstub/core/runtime/StubFactory;");
        addInstruction("ALOAD", "0");
        addInstruction("LDC", STRING_INFO_CLASS_NAME);
        addInstruction("LDC", STRING_INFO_METHOD_NAME);
        addInstruction("LDC", STRING_INFO_METHOD_DESCRIPTION);
        addInstruction("INVOKEINTERFACE", INTERFACE_METHOD_REF_CREATE_STUB, 5, 0);//"org/wooddog/woodstub/core/runtime/StubFactory.createStub (Ljava/lang/Object;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Lorg/wooddog/woodstub/core/runtime/Stub;");
        addInstruction("ASTORE", "3");

        addInstruction("ALOAD", "3");
        addInstruction("IFNULL", LABEL_CODE_ADDRESS);


        addInstruction("ALOAD", 3);
        addInstruction("ICONST_2"); // number of arguments
        addInstruction("ANEWARRAY", CLASS_INFO_STRING);

        // dyn
        addInstruction("DUP");
        addInstruction("ICONST_0");
        addInstruction("LDC", STRING_INFO_PARAMETER_NAME_0);
        addInstruction("AASTORE");


        addInstruction("ANEWARRAY", CLASS_INFO_OBJECT);

        // dyn
        addInstruction("DUP");
        addInstruction("ICONST_0");
        addInstruction("ALOAD_1");
        addInstruction("AASTORE");


        addInstruction("INVOKEINTERFACE", INTERFACE_METHOD_REF_SET_PARAMETERS, 3, 0); // "org/wooddog/woodstub/core/runtime/Stub.setParameters ([Ljava/lang/String;[Ljava/lang/Object;)V");
        addInstruction("ALOAD", 3);
        addInstruction("INVOKEINTERFACE", INTERFACE_METHOD_REF_EXECUTE, 1, 0);

        addInstruction("ALOAD", 3);
        addInstruction("INVOKEINTERFACE", INTERFACE_METHOD_REF_BEHAVIOR, 1, 0);
        addInstruction("LOOKUPSWITCH", LABEL_CODE_ADDRESS, 2, 0, LABEL_RETURN_ADDRESS, 1, LABEL_THROW_ADDRESS);

        //L6
        address = addInstruction("ALOAD", 3);
        LABEL_RETURN_ADDRESS.setReference(address);

        addInstruction("INVOKEINTERFACE", INTERFACE_METHOD_REF_RESULT, 1, 0);
        addInstruction("CHECKCAST", CLASS_INFO_INTEGER);
        addInstruction("INVOKEVIRTUAL", METHOD_REF_INFO_INT_VALUE);
        addInstruction("IRETURN");

        //L7
        address = addInstruction("ALOAD", 3);
        LABEL_THROW_ADDRESS.setReference(address);
        addInstruction("INVOKEINTERFACE", INTERFACE_METHOD_REF_RESULT, 1, 0); // org/wooddog/woodstub/core/runtime/Stub.getResult ()Ljava/lang/Object;");
        addInstruction("CHECKCAST, ", CLASS_INFO_THROWABLE);
        addInstruction("ATHROW");
    }


    private int addInstruction(String instruction, Object... parameters) {
        return 0;
    }

    public static void main(String[] args) throws IOException {

        File file = new File("C:\\git-hub\\woodstub-core\\target\\classes\\org\\wooddog\\woodstub\\core\\Template.class");
        FileInputStream stream;

        stream = new FileInputStream(file);
        new StubCodeGenerator().stubClass(stream);
        stream.close();
    }
}
