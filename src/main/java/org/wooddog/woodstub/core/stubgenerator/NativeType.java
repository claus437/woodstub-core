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
        TYPES.put('Z', new NativeType(Boolean.class, "booleanValue()Z", 'i'));
        TYPES.put('B', new NativeType(Byte.class, "byteValue()B", 'i'));
        TYPES.put('C', new NativeType(Character.class, "charValue()C", 'i'));
        TYPES.put('S', new NativeType(Short.class, "shortValue()S", 'i'));
        TYPES.put('I', new NativeType(Integer.class, "intValue()I", 'i'));
        TYPES.put('F', new NativeType(Float.class, "floatValue()F", 'f'));
        TYPES.put('D', new NativeType(Double.class, "doubleValue()D", 'd'));
        TYPES.put('J', new NativeType(Long.class, "longValue()J", 'l'));
    }

    private NativeType(Class clazz, String parseMethod, char operationPrefix) {
        this.type = clazz.getCanonicalName().replaceAll("\\.", "/");
        this.parseMethod = this.type + "#" + parseMethod;
        this.operationPrefix = operationPrefix;
        this.size = operationPrefix == 'd' || operationPrefix == 'l' ? 2 : 1;
        this.primitive = true;
    }

    private NativeType(String type) {
        this.type = type;
        this.parseMethod = null;
        this.operationPrefix = 'a';
        this.size = 1;
        this.primitive = false;
    }

    public static NativeType getNativeType(String signature) {
        NativeType nativeType;
        char type;

        type = signature.charAt(signature.indexOf(")") + 1);
        nativeType = TYPES.get(type);
        if (nativeType == null) {
            nativeType = new NativeType(signature.substring(signature.indexOf(")") + 2, signature.length() - 1));
            if (signature.charAt(signature.indexOf(")") + 1) == '[') {
                nativeType.array = true;
            }
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
}
