package org.wooddog.woodstub.core;

import org.wooddog.woodstub.core.runtime.Stub;
import org.wooddog.woodstub.core.runtime.StubFactory;

import javax.xml.parsers.FactoryConfigurationError;
import java.io.IOException;
import java.lang.instrument.Instrumentation;
import java.util.Arrays;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 22-04-11
 * Time: 18:36
 * To change this template use File | Settings | File Templates.
 */
public class WoodStub {
    private static final Stub STUB = new Stub() {
        public void setParameters(String[] names, Object[] values) {
            System.out.println("SET PARAMS " + names + " " + Arrays.asList(values));
        }

        public void execute() throws IOException {
            System.out.println("EXECUTE");
            //throw new IOException("my personal exception");
        }

        public int getBehavior() {
            System.out.println("BEHAVIOR");
            return 0;
        }

        public Object getResult() {
            System.out.println("RESULT");
            return new Integer(11);
        }
    };


    private static StubFactory DEFAULT_FACTORY = new StubFactory() {
        public Stub createStub(Object source, String clazz, String name, String description) {
            System.out.println("CALLED :O)) - " + source + " " + clazz + " " + name + " " + description);

            return new TestStub(description);
        }
    };

    private static StubFactory FACTORY = DEFAULT_FACTORY;

 	public static void premain(String agentArguments, Instrumentation instrumentation) {
		instrumentation.addTransformer(new WoodTransformer());
	}

    public static StubFactory getStubFactory() {
        return FACTORY;
    }

    public static void setStubFactory(StubFactory factory) {
        if (factory == null) {
            FACTORY = DEFAULT_FACTORY;
        } else {
            FACTORY = factory;
        }
    }
}
