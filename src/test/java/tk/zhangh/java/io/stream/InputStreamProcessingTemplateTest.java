package tk.zhangh.java.io.stream;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 测试输入流处理模板
 * Created by ZhangHao on 2016/8/9.
 */
public class InputStreamProcessingTemplateTest {

    @Test
    public void testProcess() throws Exception {
        InputStreamProcessor processor = new InputStreamProcessor() {
            @Override
            public Object process(InputStream inStream) throws IOException {
                InputStreamReader reader = new InputStreamReader(inStream, "utf-8");
                BufferedReader bufferedReader = new BufferedReader(reader);
                String line;
                while ((line = bufferedReader.readLine()) != null){
                    System.out.println(line);
                }
                return null;
            }
        };
        String filePath = System.getProperty("user.dir") + "\\README.md";
        InputStreamProcessingTemplate.process(filePath, processor);
    }
}