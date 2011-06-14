package org.wooddog.woodstub.core.stubgenerator.templates;

import org.wooddog.woodstub.core.WoodStub;
import org.wooddog.woodstub.core.runtime.Stub;

import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 14-06-11
 * Time: 15:12
 * To change this template use File | Settings | File Templates.
 */
public class GetFloat {
    public float getFloat() {
        return 0;
    }
}

class getFloatExpected extends ToSource {
    public float getFloat() throws Throwable {

        if (WoodStub.isRunning()) {
            WoodStub.pause();

            try {
                Stub stub = WoodStub.getStubFactory().createStub(this, "org/wooddog/woodstub/core/stubgenerator/templates/GetFloat#getFloat()F");

                if (stub != null) {
                    stub.setParameters(new String[]{}, new Object[]{});

                    stub.execute();
                    return ((Float) stub.getResult()).floatValue();
                }
            } finally {
                WoodStub.resume();
            }
        }

        return 0;
    }

    public static void main(String[] args) throws IOException {
        new getFloatExpected().toByteCode();
    }
}

