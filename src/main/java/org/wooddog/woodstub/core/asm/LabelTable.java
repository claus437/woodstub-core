package org.wooddog.woodstub.core.asm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 14-06-11
 * Time: 09:07
 * To change this template use File | Settings | File Templates.
 */
public class LabelTable {
    private Map<String, Integer> labels;
    private List<InstructionLabelMap> mappings;

    public LabelTable() {
        labels = new HashMap<String, Integer>();
        mappings = new ArrayList<InstructionLabelMap>();
    }

    public void setAddress(String name, int address) {
        labels.put(name, address);
        System.out.println(name + " " + address);
        remap();
    }

    public void map(Instruction instruction, int address, String label, int argumentIndex) {
        InstructionLabelMap instructionLabelMap;

        instructionLabelMap = new InstructionLabelMap();
        instructionLabelMap.address = address;
        instructionLabelMap.instruction = instruction;
        instructionLabelMap.label = label;
        instructionLabelMap.argumentIndex = argumentIndex;

        mappings.add(instructionLabelMap);
    }

    private void remap() {
        Integer address;

        for (InstructionLabelMap map : mappings) {
            address = labels.get(map.label);

            if (address != null) {
                map.instruction.setValue(map.argumentIndex, address - map.address);
            }
        }
    }

    private class InstructionLabelMap {
        Instruction instruction;
        String label;
        int argumentIndex;
        int address;
    }
}
