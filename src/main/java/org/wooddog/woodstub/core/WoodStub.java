package org.wooddog.woodstub.core;

import org.omg.CORBA.OBJECT_NOT_EXIST;
import org.wooddog.woodstub.core.instrumentation.AttributeFactory;
import org.wooddog.woodstub.core.instrumentation.StubCodeGenerator;
import org.wooddog.woodstub.core.runtime.Stub;
import org.wooddog.woodstub.core.runtime.StubFactory;

import java.io.IOException;
import java.lang.instrument.Instrumentation;
import java.util.logging.*;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 22-04-11
 * Time: 18:36
 * To change this template use File | Settings | File Templates.
 */
public class WoodStub {
    private static final Logger LOGGER = Logger.getLogger(WoodStub.class.getName());
    private static FileHandler handler;
    private static boolean running = true;

    private static StubFactory NON_STUB_FACTORY = new StubFactory() {
        public Stub createStub(Object source, String method) {
            return null;
        }
    };

    private static StubFactory FACTORY = NON_STUB_FACTORY;

 	public static void premain(String agentArguments, Instrumentation instrumentation) {

        try {
            // Create a file handler that write log record to a file called my.log
            handler = new FileHandler("woodstub.log");
            handler.setFormatter(new Formatter() {
                @Override
                public String format(LogRecord record) {
                    return record.getMessage() + "\n";
                }
            });
            handler.setLevel(Level.ALL);


            Logger.getLogger(WoodStub.class.getName()).addHandler(handler);
            Logger.getLogger(WoodTransformer.class.getName()).addHandler(handler);
            Logger.getLogger(StubCodeGenerator.class.getName()).addHandler(handler);
            Logger.getLogger(AttributeFactory.class.getName()).addHandler(handler);

            Logger.getLogger(WoodStub.class.getName()).setLevel(Level.FINEST);
            Logger.getLogger(WoodTransformer.class.getName()).setLevel(Level.FINEST);
            Logger.getLogger(StubCodeGenerator.class.getName()).setLevel(Level.FINEST);
            Logger.getLogger(AttributeFactory.class.getName()).setLevel(Level.INFO);
        } catch (IOException x) {
            LOGGER.log(Level.SEVERE, "failed creating file logger", x);
        }


        instrumentation.addTransformer(new WoodTransformer(), true);
        LOGGER.log(Level.INFO, "woodstub transformer installed successfully");
    }

    public static void begin(String method) {
    }

    public static void complete() {
    }

    public static void flushLog() {
        handler.flush();
    }

    public static StubFactory getStubFactory() {
        return FACTORY;
    }

    public static void setStubFactory(StubFactory factory) {
        if (factory == null) {
            FACTORY = NON_STUB_FACTORY;
        } else {
            FACTORY = factory;
        }
    }

    public static void resume() {
        System.out.println("resume");
        running = true;
    }

    public static void pause() {
        System.out.println("pause");
        running = false;
    }

    public static boolean isRunning() {
        System.out.println("isRunning " + running);
        return running;
    }
}
