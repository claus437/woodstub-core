package org.wooddog.woodstub.core.asm;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 24-04-11
 * Time: 20:00
 * To change this template use File | Settings | File Templates.
 */
public class Instruction  {
    private OperationDefinition definition;
    protected int[] values;

    Instruction(OperationDefinition definition) {
        this.definition = definition;
        this.values = new int[definition.getParameterDataTypes().length];
    }

    public int getCode() {
        return definition.getCode();
    }

    public String getName() {
        return definition.getName();
    }

    public char[] getParameterTypes() {
        return definition.getParameterDataTypes();
    }

    public char[] getParameterInfo() {
        return definition.getParameterPointerTypes();
    }

    public int[] getValues() {
        return values;
    }

    public void setValues(int[] values) {
        this.values = values;
    }

    public int getLength() {
        return definition.getSize();
    }


    public String toString(int[] values) {
        String s = "";

        for (Object i : values) {
            s += i + ",";
        }

        return s;
    }

    private String toString(char[] values) {
        String s = "";

        for (Object i : values) {
            s += i + ",";
        }

        return s;
    }

    public boolean isArgumentConstant(int index) {
        return definition.getParameterPointerTypes()[index] == OperationFactory.POINTER_TYPE_CONSTANT;
    }

    public void setValue(int argumentIndex, int value) {
        values[argumentIndex] = value;
    }
}
