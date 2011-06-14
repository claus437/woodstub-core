package org.wooddog.woodstub.core.stubgenerator.templates;

import org.wooddog.woodstub.core.WoodStub;
import org.wooddog.woodstub.core.runtime.Stub;

import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 14-06-11
 * Time: 15:01
 * To change this template use File | Settings | File Templates.
 */
public class GetCharacter {
    public char getCharacter() {
        return 0;
    }
}

class GetCharacterExpected extends ToSource {
    public char getCharacter() throws Throwable {
        if (WoodStub.isRunning()) {
            WoodStub.pause();

            try {
                Stub stub = WoodStub.getStubFactory().createStub(this, "org/wooddog/woodstub/core/stubgenerator/templates/GetCharacter#getCharacter()C");

                if (stub != null) {
                    stub.setParameters(new String[]{}, new Object[]{});

                    stub.execute();
                    return ((Character) stub.getResult()).charValue();
                }
            } finally {
                WoodStub.resume();
            }
        }

        return 0;
    }

    public static void main(String[] args) throws IOException {
        new GetCharacterExpected().toByteCode();
    }
}

