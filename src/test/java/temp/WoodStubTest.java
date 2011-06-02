package temp;

import junit.framework.Assert;
import org.junit.Test;
import org.wooddog.woodstub.core.WoodStub;

/**
 * Created by IntelliJ IDEA.
 * User: dencbr
 * Date: 30-05-11
 * Time: 10:04
 * To change this template use File | Settings | File Templates.
 */
public class WoodStubTest {

    @Test
    public void testPause() {
        WoodStub.pause();

        Assert.assertFalse(WoodStub.isRunning());
    }

    @Test
    public void testResume() {
        WoodStub.resume();

        Assert.assertTrue(WoodStub.isRunning());
    }
}
