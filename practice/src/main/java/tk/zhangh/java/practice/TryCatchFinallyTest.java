package tk.zhangh.java.practice;

import java.io.IOException;

/**
 * try,catch,finally执行顺序
 * Created by ZhangHao on 2017/3/18.
 */
public class TryCatchFinallyTest {
    private static String catchsAndFinally() {
        try {
            throw new IOException("");
        } catch (IOException e) {
            return "first catch";
        } catch (Exception e) {
            return "second catch";
        } finally {
            return "finally";
        }
    }

    private static String catchs() {
        try {
            throw new IOException("");
        } catch (IOException e) {
            return "first catch";
        } catch (Exception e) {
            return "second catch";
        }
    }

    private static String successFinally() {
        try {
            return "success";
        } finally {
            return "finally";
        }
    }

    public static void main(String[] args) {
        assert catchsAndFinally().equals("finally");
        assert catchs().equals("first catch");
        assert successFinally().equals("finally");
    }
}
