package org.wooddog.woodstub.core.stubgenerator;

import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 14-06-11
 * Time: 15:21
 * To change this template use File | Settings | File Templates.
 */
public class NativeType {
    private static final Map<Character, NativeType> TYPES = new HashMap<Character, NativeType>();
    private String type;
    private String parseMethod;
    private char operationPrefix;
    private int size;
    private boolean primitive;
    private boolean array;

    static {
        TYPES.put('Z', new NativeType("java/lang/Boolean", "booleanValue()Z", 'i'));
        TYPES.put('B', new NativeType("java/lang/Byte", "byteValue()B", 'i'));
        TYPES.put('C', new NativeType("java/lang/Character", "charValue()C", 'i'));
        TYPES.put('S', new NativeType("java/lang/Short", "shortValue()S", 'i'));
        TYPES.put('I', new NativeType("java/lang/Integer", "intValue()I", 'i'));
        TYPES.put('F', new NativeType("java/lang/Float", "floatValue()F", 'f'));
        TYPES.put('D', new NativeType("java/lang/Double", "doubleValue()D", 'd'));
        TYPES.put('J', new NativeType("java/lang/Long", "longValue()J", 'l'));
    }

    private NativeType(String type, String parseMethod, char operationPrefix) {
        this.type = type;
        this.parseMethod = this.type + "#" + parseMethod;
        this.operationPrefix = operationPrefix;
        this.size = operationPrefix == 'd' || operationPrefix == 'l' ? 2 : 1;
        this.primitive = true;
    }

    private NativeType(String signature) {
        this.type = getObjectType(signature);
        this.parseMethod = null;
        this.operationPrefix = 'a';
        this.size = 1;
        this.primitive = false;
        this.array = isArray(signature);
    }

    public static NativeType getNativeType(String signature) {
        NativeType nativeType;
        char type;

        if (isPrimitiveType(signature)) {
            type = signature.charAt(signature.indexOf(")") + 1);
            nativeType = TYPES.get(type);
        } else {
            nativeType = new NativeType(signature);
        }

        return nativeType;
    }

    public String getType() {
        return type;
    }

    public String getParseMethod() {
        return parseMethod;
    }

    public String getLoadOperation() {
        return operationPrefix + "load";
    }

    public String getStoreOperation() {
        return operationPrefix + "store";
    }

    public String getReturnOperation() {
        return operationPrefix + "return";
    }

    public int size() {
        return size;
    }

    public boolean isPrimitive() {
        return primitive;
    }

    public boolean isArray() {
        return array;
    }

    private String getObjectType(String signature) {
        String type;

        if (isArray(signature)) {
            type = signature.substring(signature.indexOf("["));
        } else {
            type = signature.substring(signature.indexOf(")") + 2, signature.length() - 1);
        }

        return type;
    }

    private boolean isArray(String signature) {
        return signature.charAt(signature.indexOf(")") + 1) == '[';
    }

    private static boolean isPrimitiveType(String signature) {
        char type;

        type = signature.charAt(signature.indexOf(")") + 1);

        return type != 'L' && type != '[';
    }

    private String getPrimitiveType(String signature) {
        return signature.substring(signature.indexOf(")") + 1, signature.length());
    }
}
