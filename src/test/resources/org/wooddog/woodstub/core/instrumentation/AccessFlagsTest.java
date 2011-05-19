package org.wooddog.woodstub.core.instrumentation;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by IntelliJ IDEA.
 * User: dencbr
 * Date: 19-05-11
 * Time: 09:48
 * To change this template use File | Settings | File Templates.
 */
public class AccessFlagsTest {

    @Test
    public void testHasEveryFlag() {
        int flags;

        flags = AccessFlags.FINAL +
                AccessFlags.PRIVATE +
                AccessFlags.PROTECTED +
                AccessFlags.PUBLIC +
                AccessFlags.STATIC +
                AccessFlags.TRANSIENT +
                AccessFlags.VOLATILE;

        Assert.assertTrue(AccessFlags.hasFlag(flags, AccessFlags.FINAL));
        Assert.assertTrue(AccessFlags.hasFlag(flags, AccessFlags.PRIVATE));
        Assert.assertTrue(AccessFlags.hasFlag(flags, AccessFlags.PROTECTED));
        Assert.assertTrue(AccessFlags.hasFlag(flags, AccessFlags.PUBLIC));
        Assert.assertTrue(AccessFlags.hasFlag(flags, AccessFlags.STATIC));
        Assert.assertTrue(AccessFlags.hasFlag(flags, AccessFlags.TRANSIENT));
        Assert.assertTrue(AccessFlags.hasFlag(flags, AccessFlags.VOLATILE));
    }

    @Test
    public void testHasNoFlag() {
        int flags;

        flags = 0;

        Assert.assertFalse(AccessFlags.hasFlag(flags, AccessFlags.FINAL));
        Assert.assertFalse(AccessFlags.hasFlag(flags, AccessFlags.PRIVATE));
        Assert.assertFalse(AccessFlags.hasFlag(flags, AccessFlags.PROTECTED));
        Assert.assertFalse(AccessFlags.hasFlag(flags, AccessFlags.PUBLIC));
        Assert.assertFalse(AccessFlags.hasFlag(flags, AccessFlags.STATIC));
        Assert.assertFalse(AccessFlags.hasFlag(flags, AccessFlags.TRANSIENT));
        Assert.assertFalse(AccessFlags.hasFlag(flags, AccessFlags.VOLATILE));
    }

    @Test
    public void testIsStatic() {
        Assert.assertFalse(AccessFlags.isStatic(AccessFlags.FINAL));
        Assert.assertTrue(AccessFlags.isStatic(AccessFlags.STATIC));
    }
}
