package org.wooddog.woodstub.core.stubgenerator.templates;

import org.wooddog.woodstub.core.WoodStub;
import org.wooddog.woodstub.core.runtime.Stub;

import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 14-06-11
 * Time: 16:54
 * To change this template use File | Settings | File Templates.
 */
public class GetLong {
    public long getLong() {
        return 0;
    }
}

class GetLongExpected extends ToSource {
    public long getLong() throws Throwable {
        if (WoodStub.isRunning()) {
            WoodStub.pause();

            try {
                Stub stub = WoodStub.getStubFactory().createStub(this, "org/wooddog/woodstub/core/stubgenerator/templates/GetLong#getLong()J");

                if (stub != null) {
                    stub.setParameters(new String[]{}, new Object[]{});

                    stub.execute();
                    return ((Long) stub.getResult()).longValue();
                }
            } finally {
                WoodStub.resume();
            }
        }

        return 0;
    }

    public static void main(String[] args) throws IOException {
        new GetLongExpected().toByteCode();
    }
}

