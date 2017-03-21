package tk.zhangh.toolkit;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by ZhangHao on 2017/3/18.
 */
public class CodingUtilsTest {

    private static final String content = "zhangh.tk";

    @Test
    public void testToHexString() throws Exception {
        Assert.assertEquals("7a68616e67682e746b", CodingUtils.toHexString(content.getBytes()));
    }

    @Test
    public void testDigest() throws Exception {
        Assert.assertEquals("5d29d9cb4b72059251f9d443426fa756", CodingUtils.digest(content));
    }

    @Test
    public void testDigest1() throws Exception {
        Assert.assertEquals("5d29d9cb4b72059251f9d443426fa756", CodingUtils.digest(content, "UTF-8", "MD5"));
    }

}