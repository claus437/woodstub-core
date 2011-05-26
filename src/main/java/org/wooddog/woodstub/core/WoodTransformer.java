package org.wooddog.woodstub.core;

import org.wooddog.woodstub.core.instrumentation.StubCodeGenerator;
import sun.security.krb5.internal.LoginOptions;

import java.io.*;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 22-04-11
 * Time: 18:35
 * To change this template use File | Settings | File Templates.
 */
public class WoodTransformer implements ClassFileTransformer {
    private static final Logger LOGGER = Logger.getLogger(WoodTransformer.class.getName());
    private static int count;

	public WoodTransformer() {
		super();

        MyLogger.init();
    }


	public byte[] transform(ClassLoader loader, String className, Class redefiningClass, ProtectionDomain domain, byte[] bytes) throws IllegalClassFormatException {
        StubCodeGenerator stubGenerator;
        ByteArrayInputStream source;
        ByteArrayOutputStream target;

        //System.out.println("class " + className);

        /*
        if (className != null && className.startsWith("org/wooddog/woodstub")) {
            System.out.println("skipped " + className);
            return bytes;
        }
        */

        if (loader == null) {
            System.out.println(count + "         " + className);
            return bytes;
        }
        System.out.println(count + " " + loader.hashCode() + " " + className);

        count ++;


        //if ("com/intellij/rt/execution/application/AppMain".equals(className)) return null;
        if ("com/intellij/rt/execution/application/AppMain$1".equals(className)) return null;
        if ("com/intellij/rt/execution/junit/JUnitStarter".equals(className)) return null;
        if ("com/intellij/rt/execution/junit/segments/SegmentedOutputStream".equals(className)) return null;
        if ("com/intellij/rt/execution/junit/segments/PacketProcessor".equals(className)) return null;
        if ("com/intellij/rt/execution/junit/IdeaTestRunner".equals(className)) return null;
        if ("junit/textui/ResultPrinter".equals(className)) return null;
        if ("junit/framework/TestListener".equals(className)) return null;
        if ("com/intellij/junit3/JUnit3IdeaTestRunner$MockResultPrinter".equals(className)) return null;
        if ("junit/framework/ComparisonFailure".equals(className)) return null;
        if ("junit/framework/AssertionFailedError".equals(className)) return null;
        if ("com/intellij/junit4/JUnit4IdeaTestRunner".equals(className)) return null;
        if ("org/junit/runner/Description".equals(className)) return null;
        if ("org/junit/runner/notification/RunListener".equals(className)) return null;
        if ("com/intellij/junit4/JUnit4IdeaTestRunner$1".equals(className)) return null;
        if ("com/intellij/rt/execution/junit/segments/OutputObjectRegistry".equals(className)) return null;
        if ("com/intellij/junit4/JUnit4OutputObjectRegistry".equals(className)) return null;
        if ("com/intellij/junit4/JUnit4TestResultsSender".equals(className)) return null;
        if ("junit/textui/TestRunner".equals(className)) return null;
        if ("junit/runner/BaseTestRunner".equals(className)) return null;
        if ("junit/framework/Test".equals(className)) return null;
        if ("junit/framework/TestResult".equals(className)) return null;
        if ("com/intellij/rt/execution/junit/DeafStream".equals(className)) return null;
        if ("com/intellij/rt/execution/junit/PacketFactory".equals(className)) return null;
        if ("org/junit/Ignore".equals(className)) return null;
        if ("org/junit/runner/JUnitCore".equals(className)) return null;
        if ("org/junit/internal/TextListener".equals(className)) return null;
        if ("org/junit/runner/Runner".equals(className)) return null;
        if ("org/junit/runner/Describable".equals(className)) return null;
        if ("org/junit/internal/runners/JUnit38ClassRunner".equals(className)) return null;
        if ("org/junit/runner/manipulation/Filterable".equals(className)) return null;
        if ("org/junit/runner/manipulation/Sortable".equals(className)) return null;
        if ("org/junit/internal/JUnitSystem".equals(className)) return null;
        if ("org/junit/runner/notification/RunNotifier".equals(className)) return null;
        if ("org/junit/runner/notification/StoppedByUserException".equals(className)) return null;
        if ("com/intellij/junit4/JUnit4TestRunnerUtil".equals(className)) return null;
        if ("org/junit/runner/manipulation/Filter".equals(className)) return null;
        if ("com/intellij/junit4/JUnit4TestRunnerUtil$1".equals(className)) return null;
        if ("org/junit/runner/Request".equals(className)) return null;
        if ("com/intellij/junit4/JUnit4TestRunnerUtil$2".equals(className)) return null;
        if ("org/junit/internal/requests/ClassRequest".equals(className)) return null;
        if ("com/intellij/junit4/JUnit4TestRunnerUtil$3".equals(className)) return null;
        if ("org/wooddog/woodstub/EasterAppTest".equals(className)) return null;
        if ("org/junit/internal/requests/SortingRequest".equals(className)) return null;
        if ("org/junit/internal/runners/ErrorReportingRunner".equals(className)) return null;
        if ("org/junit/internal/requests/FilterRequest".equals(className)) return null;
        if ("org/junit/runner/Request$1".equals(className)) return null;
        if ("org/junit/runners/model/InitializationError".equals(className)) return null;
        if ("org/junit/runners/model/RunnerBuilder".equals(className)) return null;
        if ("org/junit/internal/builders/AllDefaultPossibilitiesBuilder".equals(className)) return null;
        if ("org/junit/internal/builders/SuiteMethodBuilder".equals(className)) return null;
        if ("org/junit/internal/builders/NullBuilder".equals(className)) return null;
        if ("org/junit/internal/builders/IgnoredBuilder".equals(className)) return null;
        if ("org/junit/internal/builders/IgnoredClassRunner".equals(className)) return null;
        if ("org/junit/internal/builders/AnnotatedBuilder".equals(className)) return null;
        if ("org/junit/internal/runners/SuiteMethod".equals(className)) return null;
        if ("org/junit/internal/builders/JUnit3Builder".equals(className)) return null;
        if ("org/junit/internal/builders/JUnit4Builder".equals(className)) return null;
        if ("org/junit/runners/BlockJUnit4ClassRunner".equals(className)) return null;
        if ("org/junit/runners/ParentRunner".equals(className)) return null;
        if ("org/junit/runner/RunWith".equals(className)) return null;
        if ("junit/framework/TestCase".equals(className)) return null;
        if ("junit/framework/Assert".equals(className)) return null;
        if ("org/junit/runner/manipulation/NoTestsRemainException".equals(className)) return null;
        if ("org/junit/runners/model/Statement".equals(className)) return null;
        if ("org/junit/runners/ParentRunner$2".equals(className)) return null;
        if ("org/junit/internal/runners/statements/RunAfters".equals(className)) return null;
        if ("org/junit/internal/runners/statements/RunBefores".equals(className)) return null;
        if ("org/junit/internal/AssumptionViolatedException".equals(className)) return null;
        if ("org/hamcrest/SelfDescribing".equals(className)) return null;
        if ("org/junit/runners/model/RunnerScheduler".equals(className)) return null;
        if ("org/junit/internal/runners/statements/FailOnTimeout".equals(className)) return null;
        if ("org/junit/internal/runners/statements/ExpectException".equals(className)) return null;
        if ("org/junit/internal/runners/statements/InvokeMethod".equals(className)) return null;
        if ("org/junit/internal/runners/statements/Fail".equals(className)) return null;
        if ("org/junit/runner/manipulation/Sorter".equals(className)) return null;
        if ("org/junit/runner/manipulation/Sorter$1".equals(className)) return null;
        if ("org/junit/runners/ParentRunner$1".equals(className)) return null;
        if ("org/junit/runners/model/TestClass".equals(className)) return null;
        if ("org/junit/runners/model/FrameworkMember".equals(className)) return null;
        if ("org/junit/runners/model/FrameworkMethod".equals(className)) return null;
        if ("org/junit/runners/model/FrameworkField".equals(className)) return null;
        if ("org/junit/BeforeClass".equals(className)) return null;
        if ("$Proxy3".equals(className)) return null;
        if ("org/junit/Before".equals(className)) return null;
        if ("org/junit/Test".equals(className)) return null;
        if ("org/junit/Test$None".equals(className)) return null;
        if ("$Proxy4".equals(className)) return null;
        //if ("org/wooddog/woodstub/demo/EasterApp".equals(className)) return null;
        if ("org/junit/AfterClass".equals(className)) return null;
        if ("org/junit/Assert".equals(className)) return null;
        if ("org/hamcrest/Description".equals(className)) return null;
        if ("org/junit/ComparisonFailure".equals(className)) return null;
        if ("org/junit/After".equals(className)) return null;
        if ("org/junit/Rule".equals(className)) return null;
        if ("org/junit/runners/ParentRunner$4".equals(className)) return null;
        if ("com/intellij/rt/execution/junit/segments/Packet".equals(className)) return null;
        if ("com/intellij/rt/execution/junit/segments/PacketWriter".equals(className)) return null;
        if ("com/intellij/junit4/JUnit4ReflectionUtil".equals(className)) return null;
        if ("com/intellij/rt/execution/junit/segments/SegmentedStream".equals(className)) return null;
        if ("org/junit/runner/Result".equals(className)) return null;
        if ("org/junit/runner/Result$Listener".equals(className)) return null;
        if ("org/junit/runner/notification/RunNotifier$1".equals(className)) return null;
        if ("org/junit/runner/notification/RunNotifier$SafeNotifier".equals(className)) return null;
        if ("org/junit/internal/runners/model/EachTestNotifier".equals(className)) return null;
        if ("org/junit/runners/model/FrameworkMethod$1".equals(className)) return null;
        if ("org/junit/internal/runners/model/ReflectiveCallable".equals(className)) return null;
        if ("org/junit/runners/ParentRunner$3".equals(className)) return null;
        if ("org/junit/runner/notification/RunNotifier$3".equals(className)) return null;
        if ("com/intellij/rt/execution/junit/TestMeter".equals(className)) return null;
        if ("org/junit/runners/BlockJUnit4ClassRunner$1".equals(className)) return null;
        if ("org/junit/rules/MethodRule".equals(className)) return null;
        if ("org/junit/runner/notification/RunNotifier$7".equals(className)) return null;
        if ("org/junit/internal/runners/model/MultipleFailureException".equals(className)) return null;
        if ("org/junit/runner/notification/Failure".equals(className)) return null;
        if ("org/junit/runner/notification/RunNotifier$4".equals(className)) return null;
        if ("com/intellij/rt/execution/junit/KnownException".equals(className)) return null;
        if ("com/intellij/rt/execution/junit/ExceptionPacketFactory".equals(className)) return null;
        if ("com/intellij/rt/execution/junit/segments/TraceFilter".equals(className)) return null;
        if ("org/junit/runner/notification/RunNotifier$2".equals(className)) return null;
        if ("com/intellij/junit4/JUnit4IdeaTestRunner$TimeSender".equals(className)) return null;
        /*
        System.out.println("loader " + loader);

        if (loader.getResourceAsStream("org/wooddog/woodstub/core/WoodStub.class") == null) {
            System.out.println("-SKIP " + className);
            LOGGER.log(Level.FINE, "skipping " +  className + " on loader " + loader + " as found no client lib");
            return bytes;
        } else {
            System.out.println("-FOUND " + className);
        }
        */
        source = new ByteArrayInputStream(bytes);
        target = new ByteArrayOutputStream();

        try {
            MyLogger.write(className, bytes);
            stubGenerator = new StubCodeGenerator();
            stubGenerator.stubClass(className, source, target);
            MyLogger.write(className + "_WS", target.toByteArray());
            LOGGER.log(Level.FINE, "stubbed " +  className + " on loader " + loader);
        } catch (Throwable x) {
            LOGGER.log(Level.WARNING, "failed stubbing " +  className + " on loader " + loader + " " + x.getMessage(), x);
            return null;
        }

		return target.toByteArray();
	}
}
