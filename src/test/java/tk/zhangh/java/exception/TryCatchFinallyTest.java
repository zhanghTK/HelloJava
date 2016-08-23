package tk.zhangh.java.exception;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * try,catch,finally执行顺序
 * Created by ZhangHao on 2016/8/22.
 */
public class TryCatchFinallyTest {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private String test1() {
        try {
            throw new IOException("");
        } catch (IOException e) {
            return "return in first catch";
        } catch (Exception e) {
            return "return in second catch";
        } finally {
            return "return in finally";
        }
    }

    private String test2() {
        try {
            throw new IOException("");
        } catch (IOException e) {
            return "return in first catch";
        } catch (Exception e) {
            return "return in second catch";
        }
    }

    private String test3() {
        try {
            return "success";
        } catch (Exception e) {
            return "return in catch";
        } finally {
            return "return in finally";
        }
    }

    @Test
    public void testSequence() {
        String result;
        logger.info("test return in catch and finally");
        result = test1();
        Assert.assertEquals(result, "return in finally");

        logger.info("test return in many catches");
        result = test2();
        Assert.assertEquals(result, "return in first catch");

        logger.info("test return in success and finally");
        result = test3();
        Assert.assertEquals(result, "return in finally");
    }
}
