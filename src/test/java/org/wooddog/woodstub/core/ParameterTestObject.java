package org.wooddog.woodstub.core;


import org.wooddog.woodstub.core.runtime.Stub;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 06-05-11
 * Time: 22:57
 * To change this template use File | Settings | File Templates.
 */
public class ParameterTestObject {
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

    /*
    public boolean template() throws Throwable {
        Stub stub = WoodStub.getStubFactory().createStub(this, "org/wooddog/woodstub/core/runtime/Stub/ParameterTestObject", "template", "(Ljava/lang/Object;)V}");

        if (stub != null) {
            Object[] o = new Object[0];
            //o[0] = z;
            //o[1] = b;
            //o[2] = c;
            //o[3] = i;
            //o[4] = f;
            //o[5] = j;
            //o[6] = d;
            //o[7] = l;

            stub.setParameters(null, o);

            stub.execute();
            return ((Boolean) stub.getResult()).booleanValue();
        }

        throw new RuntimeException("not thrown");
    }



    /*

    public void parameterObject(Object o) {
        System.out.println("object " + o);
    }

    public void parameterArray(int[] i) {
        System.out.println("[integer " + i);
    }


    public void parameter2Array(int[][] i) {
        System.out.println("[[integer " + i);
    }


    public void all(boolean z, byte b, char c, short s, int i, float f, long j, double d, Object l, Object[] a) {
        System.out.println("all " + z + " " + b + " " + c + " " + s + " " + i + " " + f + " " + j + " " + d + " " + l + a);
    }

    public void non() throws Throwable {
        System.out.println("non");
    }
    */

    public void setTemplate(int[] i) throws Throwable {
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
            return;
        }

        throw new RuntimeException("not thrown");
    }

}
