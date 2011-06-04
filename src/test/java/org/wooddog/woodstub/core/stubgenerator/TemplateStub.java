package org.wooddog.woodstub.core.stubgenerator;

import org.wooddog.woodstub.core.WoodStub;
import org.wooddog.woodstub.core.runtime.Stub;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.MalformedInputException;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 02-06-11
 * Time: 14:35
 * To change this template use File | Settings | File Templates.
 */
public class TemplateStub {

    public boolean getBoolean() throws Throwable {
        if (WoodStub.isRunning()) {
            WoodStub.pause();

            try {
                Stub stub = WoodStub.getStubFactory().createStub(this, "org/wooddog/woodstub/core/StubTemplate#getBoolean()B");

                if (stub != null) {
                    stub.setParameters(new String[]{}, new Object[]{});

                    stub.execute();
                    return ((Boolean) stub.getResult()).booleanValue();
                }
            } finally {
                WoodStub.resume();
            }
        }

        return false;
    }
}
