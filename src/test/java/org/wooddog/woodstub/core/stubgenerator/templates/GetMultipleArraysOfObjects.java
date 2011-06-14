package org.wooddog.woodstub.core.stubgenerator.templates;

import org.wooddog.woodstub.core.WoodStub;
import org.wooddog.woodstub.core.runtime.Stub;

import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 14-06-11
 * Time: 22:36
 * To change this template use File | Settings | File Templates.
 */
public class GetMultipleArraysOfObjects {
    public Object[][] getMultipleArraysOfObjects() {
        return null;
    }
}

class GetMultipleArraysOfObjectsExpected extends ToSource {
    public Object[][] getMultipleArraysOfObjects () throws Throwable {
        if (WoodStub.isRunning()) {
            WoodStub.pause();

            try {
                Stub stub = WoodStub.getStubFactory().createStub(this, "org/wooddog/woodstub/core/stubgenerator/templates/GetMultipleArraysOfObjects#getMultipleArraysOfObjects()[[Ljava/lang/Object;");

                if (stub != null) {
                    stub.setParameters(new String[]{}, new Object[]{});

                    stub.execute();
                    return (Object[][]) stub.getResult();
                }
            } finally {
                WoodStub.resume();
            }
        }

        return null;
    }

    public static void main(String[] args) throws IOException {
        new GetMultipleArraysOfObjectsExpected().toByteCode();
    }
}


