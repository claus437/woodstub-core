package org.wooddog.woodstub.core.formatter;

import com.sun.corba.se.impl.orbutil.closure.Constant;
import com.sun.org.apache.bcel.internal.classfile.ConstantFieldref;
import com.sun.org.apache.bcel.internal.generic.NamedAndTyped;
import org.wooddog.woodstub.core.ConstantPoolEntry;
import org.wooddog.woodstub.core.InternalErrorException;
import org.wooddog.woodstub.core.asm.Instruction;
import org.wooddog.woodstub.core.asm.InstructionDefinition;
import org.wooddog.woodstub.core.asm.InstructionReader;
import org.wooddog.woodstub.core.instrumentation.*;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 02-06-11
 * Time: 16:15
 * To change this template use File | Settings | File Templates.
 */
public class CodeFormatter {
    private ConstantPool pool;

    public CodeFormatter(ConstantPool pool) {
        this.pool = pool;
    }

    public void write(AttributeCode code, StringBuffer out) throws IOException {
        InstructionReader reader;
        List<Instruction> instructionList;
        int address;
        int maxWidth;

        out.append("MAX STACK: " + code.getMaxStack());
        out.append(", MAX LOCAL " + code.getMaxLocals());
        out.append("\n");

        reader = new InstructionReader();
        reader.readBlock(new DataInputStream(new ByteArrayInputStream(code.getCode())), code.getCode().length);

        instructionList = reader.getInstructions();
        address = 0;
        maxWidth = Integer.toString(code.getCode().length).length();
        for (Instruction instruction : instructionList) {
            StringFormatter.alignRight(out, maxWidth, address);
            out.append(" ");
            write(out, instruction);
            out.append("\n");
            address += instruction.getLength();
        }
        out.append("\n");

        write(code.getExceptions(), out);
        out.append("\n");

        for (Attribute attribute : code.getAttributes()) {
            write(attribute, out);
            out.append("\n");
        }
    }

    public void write(List<TableException> exceptionList, StringBuffer out) {
        out.append("EXCEPTIONS\n");
        out.append("SPC   EPC   HPC   CTP\n");

        for (TableException exception : exceptionList) {
            write(exception, out);
        }

    }
    public void write(Attribute attribute, StringBuffer out) {
        if (attribute instanceof AttributeLocalVariable) {
            write((AttributeLocalVariable) attribute, out);
            return;
        }

        if (attribute instanceof AttributeLineNumber) {
            write((AttributeLineNumber) attribute, out);
            return;
        }

        out.append("UNKNOWN ATTRIBUTE" + attribute.getName());
    }

    public void write(AttributeLineNumber lineNumber, StringBuffer out) {
        out.append("LINE NUMBERS\n");
        out.append("SPC    LINE\n");
        for (TableLineNumber line : lineNumber.getLineNumberList()) {
            StringFormatter.alignRight(out, 5, line.getStartPc());
            StringFormatter.alignRight(out, 6, line.getLineNumber());
            out.append("\n");
        }
    }

    public void write(AttributeLocalVariable localVariable, StringBuffer out) {
        out.append("LOCAL VARIABLES\n");
        out.append("ID    SPC   LEN   NAME\n");
        for (TableEntryLocalVariable variable : localVariable.getLocalVariableList()) {
            StringFormatter.alignRight(out, 5, variable.getIndex());
            StringFormatter.alignRight(out, 6, variable.getStartPc());
            StringFormatter.alignRight(out, 6, variable.getLength());
            out.append(" ");
            out.append(((ConstantUtf8Info)pool.get(variable.getNameIndex())).getValue());
            out.append(" ");
            out.append(((ConstantUtf8Info)pool.get(variable.getDescriptorIndex())).getValue());
            out.append("\n");
        }
    }

    public void write(TableException exception, StringBuffer out) {
        StringFormatter.alignRight(out, 5, exception.getStartPc());
        StringFormatter.alignRight(out, 6, exception.getEndPc());
        StringFormatter.alignRight(out, 6, exception.getHandlerPc());
        StringFormatter.alignRight(out, 6, exception.getCatchType());
        out.append("\n");
    }

    public void write(StringBuffer out, Instruction instruction) {
        char[] infoCodes;
        int[] values;

        out.append(instruction.getName());
        infoCodes = instruction.getParameterInfo();
        values = instruction.getValues();

        for (int i = 0; i < infoCodes.length; i++) {
            if (infoCodes[i] == 'P') {
                out.append(" " + lookup(values[i]));
            } else {
                out.append(" " + values[i]);
            }
        }
    }

    private String lookup(int index) {
        StringBuffer result;
        ConstantPoolInfo entry;

        entry = pool.get(index);
        if (entry instanceof ConstantPoolValue) {
            return ((ConstantPoolValue) entry).getValue().toString();
        }

        if (entry instanceof ConstantClassInfo) {
            return getClassNameByClassIndex(index);
        }

        if (entry instanceof ConstantPoolReference) {
            result = new StringBuffer();
            ConstantPoolReference reference;

            reference = (ConstantPoolReference) entry;
            result.append(getClassNameByClassIndex(reference.getClassIndex()));
            result.append("#");
            result.append(getNameByNameAndTypeIndex(reference.getNameAndTypeIndex()));
            result.append(getTypeByNameAndTypeIndex(reference.getNameAndTypeIndex()));

            return result.toString();
        }

        if (entry instanceof ConstantNameAndTypeInfo) {
            result = new StringBuffer();
            ConstantNameAndTypeInfo nameAndTypeInfo;

            nameAndTypeInfo = (ConstantNameAndTypeInfo) entry;
            result.append(getNameByNameAndTypeIndex(nameAndTypeInfo.getNameIndex()));
            result.append(getTypeByNameAndTypeIndex(nameAndTypeInfo.getDescriptorIndex()));

            return result.toString();
        }


        if (entry instanceof ConstantStringInfo) {
            return (((ConstantUtf8Info) pool.get(((ConstantStringInfo) entry).getStringIndex())).getValue());
        }

        throw new InternalErrorException("unknown constant " + entry);
    }

    public String getTypeByNameAndTypeIndex(int index) {
        ConstantNameAndTypeInfo nameAndTypeInfo;

        nameAndTypeInfo = (ConstantNameAndTypeInfo) pool.get(index);
        return ((ConstantUtf8Info) pool.get(nameAndTypeInfo.getDescriptorIndex())).getValue();
    }

    public String getNameByNameAndTypeIndex(int index) {
        ConstantNameAndTypeInfo nameAndTypeInfo;

        nameAndTypeInfo = (ConstantNameAndTypeInfo) pool.get(index);
        return ((ConstantUtf8Info) pool.get(nameAndTypeInfo.getNameIndex())).getValue();
    }

    private String getClassNameByClassIndex(int index) {
        return getClassNameByNameIndex(((ConstantClassInfo) pool.get(index)).getNameIndex());
    }

    private String getClassNameByNameIndex(int index) {
        return ((ConstantUtf8Info) pool.get(index)).getValue();
    }
    /*
    private String lookup(int index) {
        ConstantPoolInfo entry;

        entry = pool.get(index);
        if (entry instanceof ConstantPoolValue) {
            return ((ConstantPoolValue) entry).getValue().toString();
        } else {
            return lookup()
        }
    }
    */
}
