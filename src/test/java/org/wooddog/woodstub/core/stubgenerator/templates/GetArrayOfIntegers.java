package org.wooddog.woodstub.core.stubgenerator.templates;

import org.wooddog.woodstub.core.WoodStub;
import org.wooddog.woodstub.core.runtime.Stub;

import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 14-06-11
 * Time: 19:03
 * To change this template use File | Settings | File Templates.
 */
public class GetArrayOfIntegers {
    public int[] getArrayOfIntegers() {
        return null;
    }
}

class GetArrayOfIntegersExpected extends ToSource {
    public int[] getArrayOfIntegers() throws Throwable {
        if (WoodStub.isRunning()) {
            WoodStub.pause();

            try {
                Stub stub = WoodStub.getStubFactory().createStub(this, "org/wooddog/woodstub/core/stubgenerator/templates/GetArrayOfObjects#getArrayOfIntegers()[LI;");

                if (stub != null) {
                    stub.setParameters(new String[]{}, new Object[]{});

                    stub.execute();
                    return (int[]) stub.getResult();
                }
            } finally {
                WoodStub.resume();
            }
        }

        return null;
    }

    public static void main(String[] args) throws IOException {
        new GetArrayOfIntegersExpected().toByteCode();
    }
}


