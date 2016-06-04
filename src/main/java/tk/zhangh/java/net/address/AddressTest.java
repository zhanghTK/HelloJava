package tk.zhangh.java.net.address;

import java.net.InetAddress;
import java.net.InetSocketAddress;

/**
 * InetAddress:封装计算机的ip地址和DNS，不包含端口号
 * InetSocketAddress:封装计算机ip和端口
 * Created by ZhangHao on 2016/6/4.
 */
public class AddressTest {
    public static void main(String[] args) throws Exception{
        InetAddress address = InetAddress.getLocalHost();
        System.out.println(address.getHostAddress());
        System.out.println(address.getHostName());

        address = InetAddress.getByName("www.baidu.com");
        System.out.println(address.getHostAddress());
        System.out.println(address.getHostName());

        InetSocketAddress inetSocketAddress = new InetSocketAddress("localhost", 8080);
        System.out.println(inetSocketAddress.getHostName());
        System.out.println(inetSocketAddress.getAddress());
        System.out.println(inetSocketAddress.getPort());
        address = inetSocketAddress.getAddress();
        System.out.println(address.getHostAddress());
        System.out.println(address.getHostName());
    }
}
