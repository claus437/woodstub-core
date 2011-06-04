package org.wooddog.woodstub.core.stubgenerator;

import org.wooddog.woodstub.core.WoodStub;
import org.wooddog.woodstub.core.runtime.Stub;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 02-06-11
 * Time: 14:59
 * To change this template use File | Settings | File Templates.
 */
public class GetBooleanExpected {

    public boolean getBoolean() throws Throwable {
        try {
            WoodStub.begin("org/wooddog/woodstub/core/WoodStub/StubTemplate#getBoolean()B");

            if (WoodStub.isRunning()) {
                WoodStub.pause();

                try {
                    Stub stub = WoodStub.getStubFactory().createStub(this, "org/wooddog/woodstub/core/GetBoolean#getBoolean()B");

                    if (stub != null) {
                        stub.setParameters(new String[]{}, new Object[]{});

                        stub.execute();
                        return ((Boolean) stub.getResult()).booleanValue();
                    }
                } finally {
                    WoodStub.resume();
                }
            }
            return Boolean.FALSE;
        } finally {
            WoodStub.complete();
        }
    }
}
