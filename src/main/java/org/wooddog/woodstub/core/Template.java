package org.wooddog.woodstub.core;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 24-04-11
 * Time: 00:57
 * To change this template use File | Settings | File Templates.
 */
public class Template {
   public Template() {
        System.out.println(100);

        for (int i = 0; i < 100; i++) {
            System.out.println(i);
        }
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
    }}
