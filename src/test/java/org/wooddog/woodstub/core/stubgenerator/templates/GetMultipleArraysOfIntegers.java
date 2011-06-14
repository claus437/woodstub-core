package org.wooddog.woodstub.core.stubgenerator.templates;

import org.wooddog.woodstub.core.WoodStub;
import org.wooddog.woodstub.core.runtime.Stub;

import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 14-06-11
 * Time: 22:48
 * To change this template use File | Settings | File Templates.
 */
public class GetMultipleArraysOfIntegers {
    public int[][] getMultipleArraysOfIntegers() {
        return null;
    }
}

class GetMultipleArraysOfIntegersExpected extends ToSource {
    public int[][] getMultipleArraysOfIntegers() throws Throwable {
        if (WoodStub.isRunning()) {
            WoodStub.pause();

            try {
                Stub stub = WoodStub.getStubFactory().createStub(this, "org/wooddog/woodstub/core/stubgenerator/templates/GetMultipleArraysOfIntegers#getMultipleArraysOfIntegers()[[I");

                if (stub != null) {
                    stub.setParameters(new String[]{}, new Object[]{});

                    stub.execute();
                    return (int[][]) stub.getResult();
                }
            } finally {
                WoodStub.resume();
            }
        }

        return null;
    }

    public static void main(String[] args) throws IOException {
        new GetMultipleArraysOfIntegersExpected().toByteCode();
    }
}

