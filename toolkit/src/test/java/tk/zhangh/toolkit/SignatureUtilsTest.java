package tk.zhangh.toolkit;

import org.junit.Assert;
import org.junit.Test;

import java.security.SignatureException;

/**
 * 服务测试签名测试
 * Created by ZhangHao on 2017/3/18.
 */
public class SignatureUtilsTest {
    private static final String CONTENT = "zhangh.tk";

    @Test
    public void sign() throws SignatureException {
        Assert.assertNotNull(getSignData());
    }

    @Test
    public void verify() throws SignatureException {
        String path = SignatureUtils.class.getResource("/").getPath() + "key/public/rsaprivatekeyt.cer";
        SignatureUtils verify = SignatureUtils.getVerifyInstance(path);
        Assert.assertTrue(verify.verifyData(getSignData(), CONTENT));
    }

    private byte[] getSignData() throws SignatureException {
        String path = SignatureUtils.class.getResource("/").getPath() + "/key/private/ca.private.der";
        SignatureUtils sign = SignatureUtils.getSignInstance(path);
        return sign.signData(CONTENT);
    }
}