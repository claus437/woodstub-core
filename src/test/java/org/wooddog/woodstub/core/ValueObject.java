package org.wooddog.woodstub.core;


import org.wooddog.woodstub.core.runtime.Stub;

/**
 * This Class is used only during compile time, runtime it is replaced by CleanValueObject.class found in resources
 *
 */
public class ValueObject {
    private boolean booleanValue;
    private byte byteValue;
    private char charValue;
    private short shortValue;
    private int integerValue;
    private float floatValue;
    private double doubleValue;
    private long longValue;
    private Object objectValue;
    private int[] array2DValue;
    private int[][] array3DValue;

    public boolean getBooleanValue() {
        return booleanValue;
    }

    public void setBooleanValue(boolean booleanValue) {
        this.booleanValue = booleanValue;
    }

    public byte getByteValue() {
        return byteValue;
    }

    public void setByteValue(byte byteValue) {
        this.byteValue = byteValue;
    }

    public char getCharValue() {
        return charValue;
    }

    public void setCharValue(char charValue) {
        this.charValue = charValue;
    }

    public short getShortValue() {
        return shortValue;
    }

    public void setShortValue(short shortValue) {
        this.shortValue = shortValue;
    }

    public int getIntegerValue() {
        return integerValue;
    }

    public void setIntegerValue(int integerValue) {
        this.integerValue = integerValue;
    }

    public float getFloatValue() {
        return floatValue;
    }

    public void setFloatValue(float floatValue) {
        this.floatValue = floatValue;
    }

    public double getDoubleValue() {
        return doubleValue;
    }

    public void setDoubleValue(double doubleValue) {
        this.doubleValue = doubleValue;
    }

    public long getLongValue() {
        return longValue;
    }

    public void setLongValue(long longValue) {
        this.longValue = longValue;
    }

    public Object getObjectValue() {
        return objectValue;
    }

    public void setObjectValue(Object objectValue) {
        this.objectValue = objectValue;
    }

    public int[] getArray2DValue() {
        return array2DValue;
    }

    public void setArray2DValue(int[] array2DValue) {
        this.array2DValue = array2DValue;
    }

    public int[][] getArray3DValue() {
        return array3DValue;
    }

    public void setArray3DValue(int[][] array3DValue) {
        this.array3DValue = array3DValue;
    }

    public void setAll(
            boolean booleanValue,
            byte byteValue,
            char charValue,
            short shortValue,
            int integerValue,
            float floatValue,
            double doubleValue,
            long longValue,
            Object objectValue,
            int[] array2DValue,
            int[][] array3DValue) {

        this.booleanValue = booleanValue;
        this.byteValue = byteValue;
        this.charValue = charValue;
        this.shortValue = shortValue;
        this.integerValue = integerValue;
        this.floatValue = floatValue;
        this.doubleValue = doubleValue;
        this.longValue = longValue;
        this.objectValue = objectValue;
        this.array2DValue = array2DValue;
        this.array3DValue = array3DValue;
    }



    public void setTemplate(int i) throws Throwable {
        Stub stub = WoodStub.getStubFactory().createStub(this, "org/wooddog/woodstub/core/runtime/Stub/ParameterTestObject", "template", "(Ljava/lang/Object;)V}");

        if (stub != null) {
            Object[] o = new Object[1];
            o[0] = i;
            //o[1] = b;
            //o[2] = c;
            //o[3] = i;
            //o[4] = f;
            //o[5] = j;
            //o[6] = d;
            //o[7] = l;

            stub.setParameters(null, o);

            stub.execute();
            stub.getResult();

            return;
        }

        throw new RuntimeException("not thrown");
    }


    public int getTemplate() throws Throwable {
        Stub stub = WoodStub.getStubFactory().createStub(this, "org/wooddog/woodstub/core/runtime/Stub/ParameterTestObject", "template", "(Ljava/lang/Object;)V}");

        if (stub != null) {
            Object[] o = new Object[0];
            //o[1] = b;
            //o[2] = c;
            //o[3] = i;
            //o[4] = f;
            //o[5] = j;
            //o[6] = d;
            //o[7] = l;

            stub.setParameters(null, o);

            stub.execute();
            return ((Integer)stub.getResult()).intValue();
        }

        return 10;
    }

}
