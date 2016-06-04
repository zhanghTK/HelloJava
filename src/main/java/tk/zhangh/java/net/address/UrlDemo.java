package tk.zhangh.java.net.address;

import java.io.*;
import java.net.URL;

/**
 * Url demo
 * Created by ZhangHao on 2016/6/4.
 */
public class UrlDemo {
    public static void main(String[] args) throws Exception{
        UrlDemo urlDemo = new UrlDemo();
        urlDemo.urlInfo();
        urlDemo.getResources();
    }

    void urlInfo()throws Exception{
        URL url = new URL("http://www.baidu.com:80");
        URL newUrl = new URL(url, "/index.php#aa?username=zhangsan");
        System.out.println(newUrl.toString());
        System.out.println("协议：" + newUrl.getProtocol());
        System.out.println("域名：" + newUrl.getHost());
        // 存在锚点返回null
        System.out.println("端口：" + newUrl.getPort());
        System.out.println("资源：" + newUrl.getFile());
        System.out.println("相对资源：" + newUrl.getPath());
        System.out.println("锚点：" + newUrl.getRef());
        System.out.println("参数：" + newUrl.getQuery());
    }

    void getResources()throws Exception{
        URL url = new URL("http://www.baidu.com");
        File file = new File("baidu.html");
        BufferedReader bufferedReader =
                new BufferedReader(new InputStreamReader(url.openStream(), "utf-8"));
        BufferedWriter bufferedWriter =
                new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "utf-8"));
        String msg;
        while ((msg = bufferedReader.readLine()) != null){
            bufferedWriter.append(msg);
            bufferedWriter.newLine();
        }
        bufferedWriter.flush();
        bufferedWriter.close();
        bufferedReader.close();
    }
}
