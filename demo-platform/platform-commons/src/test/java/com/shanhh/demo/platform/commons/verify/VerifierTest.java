package com.shanhh.demo.platform.commons.verify;

import org.testng.annotations.Test;

/**
 * Date: 14-1-14
 * Time: 下午6:44
 *
 * @author jack.zhang
 */
public class VerifierTest {
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testIsNumberic() {
        new Verifier().isNumberic(null, "input error")
                .throwIfError();
    }
}
