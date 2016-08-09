package tk.zhangh.java.io;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * ��������������ģ��
 * Created by ZhangHao on 2016/8/9.
 */
public class InputStreamProcessingTemplateTest {

    @Test
    public void testProcess() throws Exception {
        InputStreamProcessor processor = new InputStreamProcessor() {
            @Override
            public void process(InputStream input) throws IOException {
                InputStreamReader reader = new InputStreamReader(input, "utf-8");
                BufferedReader bufferedReader = new BufferedReader(reader);
                String line;
                while ((line = bufferedReader.readLine()) != null){
                    System.out.println(line);
                }
            }
        };
        String filePath = System.getProperty("user.dir") + "\\README.md";
        InputStreamProcessingTemplate.process(filePath, processor);
    }
}