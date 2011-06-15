package org.wooddog.woodstub.core.stubgenerator.templates;

import org.wooddog.woodstub.core.WoodStub;
import org.wooddog.woodstub.core.runtime.Stub;

import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 15-06-11
 * Time: 10:17
 * To change this template use File | Settings | File Templates.
 */
public class SetBoolean {
    public void setBoolean(boolean bool) {
    }
}

class SetBooleanExpected extends ToSource {
    public void setBoolean(boolean bool) throws Throwable {
        if (WoodStub.isRunning()) {
            WoodStub.pause();

            try {
                Stub stub = WoodStub.getStubFactory().createStub(this, "org/wooddog/woodstub/core/stubgenerator/templates/SetBoolean#SetBoolean(Z)V");

                if (stub != null) {
                    stub.setParameters(new String[]{"bool"}, new Object[]{bool});

                    stub.execute();
                }
            } finally {
                WoodStub.resume();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        new SetBooleanExpected().toByteCode();
    }
}


