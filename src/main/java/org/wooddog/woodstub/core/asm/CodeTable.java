package org.wooddog.woodstub.core.asm;

import com.sun.org.apache.xpath.internal.operations.Operation;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 14-06-11
 * Time: 10:54
 * To change this template use File | Settings | File Templates.
 */
public class CodeTable implements Iterable {
    private List<CodeEntry> list;
    private int address;

    public CodeTable() {
        list = new ArrayList<CodeEntry>();
    }

    public void add(Instruction instruction) {
        CodeEntry entry;

        entry = new CodeEntry();
        entry.address = address;
        entry.instruction = instruction;

        list.add(entry);

        address += instruction.getLength();
    }

    public int getAddress() {
        return address;
    }

    private class CodeEntry {
        int address;
        Instruction instruction;
    }

    public Iterator iterator() {
        return list.iterator();
    }

    public void write(OperationWriter writer) throws IOException {
        for (CodeEntry entry : list) {
            writer.write(entry.instruction);
        }
    }
}
