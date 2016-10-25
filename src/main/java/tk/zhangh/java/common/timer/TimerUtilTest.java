package tk.zhangh.java.common.timer;

import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 方法运行时长统计测试
 *
 * Created by ZhangHao on 2016/10/25.
 */
public class TimerUtilTest {
    MethodTimerHelper helper;

    @Timer
    public void run_0_ms() {}

    @Timer
    public void run_60_ms() throws Exception {
        TimeUnit.MILLISECONDS.sleep(60);
    }

    @Timer
    public void run_print_100_times() {
        for (int i = 0; i < 10000; i++) {
            System.out.print("");
        }
    }

//    @Timer
//    public void run_with_parameter(String parameter) {
//
//    }

    @Before
    public void setup() {
        helper = new MethodTimerHelper();
    }

    @Test
    public void print_method_duration_with_a_class() throws Exception {
        helper.printMethodsDuration(TimerUtilTest.class);
    }

    @Test
    public void print_method_duration_with_a_list() throws Exception {
        List<Class> list = new ArrayList<>();
        list.add(TimerUtilTest.class);
        helper.printMethodsDuration(list);
    }
}