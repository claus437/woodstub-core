package org.wooddog.woodstub.core;


import org.wooddog.woodstub.core.runtime.Stub;

/**
 * This Class is used only during compile time, runtime it is replaced by CleanValueObject.class found in resources
 *
 */
public class ValueObject {
    private boolean booleanValue = false;
    private byte byteValue = Byte.MIN_VALUE;
    private char charValue = Character.MIN_VALUE;
    private short shortValue = Short.MIN_VALUE;
    private int integerValue = Integer.MIN_VALUE;
    private float floatValue = Float.MIN_VALUE;
    private double doubleValue = Double.MIN_VALUE;
    private long longValue = Long.MIN_VALUE;
    private Object objectValue = null;
    private int[] array2DValue = null;
    private int[][] array3DValue = null;

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
}
