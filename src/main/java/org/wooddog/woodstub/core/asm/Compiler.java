package org.wooddog.woodstub.core.asm;

import org.wooddog.woodstub.core.instrumentation.ConstantPool;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: dencbr
 * Date: 30-05-11
 * Time: 16:55
 * To change this template use File | Settings | File Templates.
 */
public class Compiler {
    private List<Operation> operations;
    private List<Instruction> codeTable;
    private Map<String, Integer> labels;
    private ConstantPool pool;

    public Compiler(ConstantPool pool) {
        this.pool = pool;
        this.operations = new ArrayList<Operation>();
    }


    public void add(String operation, Object... args) {
        operations.add(new Operation(operation,  args));
    }


    public byte[] compile() throws IOException {
        int address;
        InstructionDefinition def;
        Instruction instruction;
        int[] values;

        address = 0;

        for (int i = 0; i < operations.size(); i++) {
            if ("label".equals(operations.get(i).operation)) {
                labels.put((String) operations.get(i).args[0], address);
                continue;
            }

            def = CodeTable.getInstructionDefinition(operations.get(i).operation);
            values = resolveValues(def, operations.get(i));

            instruction = new Instruction(def);
            instruction.setValues(values);

            address += instruction.getLength();
            codeTable.add(instruction);
        }

        return compile(codeTable);
    }

    private int[] resolveValues(InstructionDefinition def, Operation operation) {

    }

    private byte[] compile(List<Instruction> codeTable) throws IOException {
        ByteArrayOutputStream stream;
        DataOutputStream dataStream;

        stream = new ByteArrayOutputStream();
        dataStream = new DataOutputStream(stream);

        for (Instruction instruction : codeTable) {
            instruction.write(dataStream);
        }

        dataStream.close();

        return stream.toByteArray();
    }


    private class Operation {
        String operation;
        Object[] args;

        private Operation(String operation, Object[] args) {
            this.operation = operation;
            this.args = args;
        }
    }
}
