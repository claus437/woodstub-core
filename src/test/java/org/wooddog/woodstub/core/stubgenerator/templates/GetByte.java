package org.wooddog.woodstub.core.stubgenerator.templates;

import org.wooddog.woodstub.core.WoodStub;
import org.wooddog.woodstub.core.runtime.Stub;

import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 14-06-11
 * Time: 12:53
 * To change this template use File | Settings | File Templates.
 */
public class GetByte {
    public byte getByte() {
        return 0;
    }
}

class GetByteExpected extends ToSource {

    public byte getByte() throws Throwable {
        if (WoodStub.isRunning()) {
            WoodStub.pause();

            try {
                Stub stub = WoodStub.getStubFactory().createStub(this, "org/wooddog/woodstub/core/stubgenerator/templates/GetByte#getByte()B");

                if (stub != null) {
                    stub.setParameters(new String[]{}, new Object[]{});

                    stub.execute();
                    return ((Byte) stub.getResult()).byteValue();
                }
            } finally {
                WoodStub.resume();
            }
        }

        return 0;
    }

    public static void main(String[] args) throws IOException {
        new GetByteExpected().toByteCode();
    }
}

