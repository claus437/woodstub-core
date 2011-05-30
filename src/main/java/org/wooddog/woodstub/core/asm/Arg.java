package org.wooddog.woodstub.core.asm;

/**
 * Created by IntelliJ IDEA.
 * User: dencbr
 * Date: 30-05-11
 * Time: 16:58
 * To change this template use File | Settings | File Templates.
 */
public class Arg {
    public static Arg value(int value) {
        return new Value(value);
    }

    public static Arg methodRef(String name) {
        return new MethodReference();
    }

    public static Arg label(String name) {
        return new Label();
    }

    static class Value extends Arg {
        private int value;

        public Value(int value) {
            this.value = value;
        }
    }

    static class MethodReference extends Arg {
    }

    static class Label extends Arg {
    }
}
