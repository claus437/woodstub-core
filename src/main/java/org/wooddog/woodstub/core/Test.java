package org.wooddog.woodstub.core;

import sun.reflect.ConstantPool;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Test {
    private int myInteger = 100;

    public Test() {
        System.out.println(100);

        for (int i = 0; i < 100; i++) {
            System.out.println(i);
        }

        ConstantPool pool = new ConstantPool();

    }

    public int methodA(String value) throws Throwable {
        if (WoodStub.isStubbed("org/wooddog/woodstub/core/Test", "methodA", "(Ljava/lang/String;)I}")) {

            String[] parameterValues = new String[] {value};

            Result result = WoodStub.evaluate(this, "org/wooddog/woodstub/core/Test", "methodA", "(Ljava/lang/String;)I}", parameterValues);
            if (result != null) {
                if (result.getException() != null) {
                    throw result.getException();
                }

                return result.getInt();
            }
        }

        System.out.println("I WAS NOT STUBBED");

        return 10;
    }
}
