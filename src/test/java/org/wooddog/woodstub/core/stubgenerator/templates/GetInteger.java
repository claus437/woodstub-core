package org.wooddog.woodstub.core.stubgenerator.templates;

import org.wooddog.woodstub.core.WoodStub;
import org.wooddog.woodstub.core.runtime.Stub;

import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 14-06-11
 * Time: 15:07
 * To change this template use File | Settings | File Templates.
 */
public class GetInteger {
    public int getInteger() {
        return 0;
    }
}

class GetIntegerExpected extends ToSource {
    public int getInteger() throws Throwable {
        if (WoodStub.isRunning()) {
            WoodStub.pause();

            try {
                Stub stub = WoodStub.getStubFactory().createStub(this, "org/wooddog/woodstub/core/stubgenerator/templates/GetInteger#getInteger()I");

                if (stub != null) {
                    stub.setParameters(new String[]{}, new Object[]{});

                    stub.execute();
                    return ((Integer) stub.getResult()).intValue();
                }
            } finally {
                WoodStub.resume();
            }
        }

        return 0;
    }

    public static void main(String[] args) throws IOException {
        new GetIntegerExpected().toByteCode();
    }
}

