package tk.zhangh.java.x.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tk.zhangh.toolkit.IoUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;

import static tk.zhangh.java.x.server.ServerUtils.BLANK;
import static tk.zhangh.java.x.server.ServerUtils.CRLF;

/**
 * 响应
 * Created by ZhangHao on 2017/2/24.
 */
public class Response {
    private Logger logger = LoggerFactory.getLogger(Response.class);
    private StringBuilder headInfo = new StringBuilder();
    private StringBuilder content = new StringBuilder();
    private PrintWriter bufferedWriter;

    public Response(Socket client) throws IOException {
        bufferedWriter = new PrintWriter(new OutputStreamWriter(client.getOutputStream()));
    }

    public Response(OutputStream outputStream) {
        bufferedWriter = new PrintWriter(new OutputStreamWriter(outputStream));
    }

    public Response print(String info) {
        content.append(info);
        return this;
    }

    public Response println(String info) {
        info += CRLF;
        return print(info);
    }

    private void createHeadInfo(int code) {
        headInfo.append("HTTP/1.1").append(BLANK).append(code).append(BLANK);
        switch (code) {
            case 200:
                headInfo.append("OK");
                break;
            case 404:
                headInfo.append("NOT FOUND");
                break;
            case 500:
                headInfo.append("SERVER ERROR");
                break;
        }
        headInfo.append(CRLF);
        headInfo.append("Server:zhanghTK Server/0.0.1").append(CRLF);
        headInfo.append("Date:").append(new Date()).append(CRLF);
        headInfo.append("Content-Type:text/html;charset=utf-8").append(CRLF);
        headInfo.append("Content-Length:").append(content.toString().getBytes()).append(CRLF);
        headInfo.append(CRLF);
    }

    public void pushToClient(int code) throws IOException {
        createHeadInfo(code);
        bufferedWriter.append(headInfo).append(content).flush();
        logger.info("response:header:{},content:{}", headInfo.toString(), content.toString());
        close();
    }

    public void close() {
        IoUtils.close(bufferedWriter);
    }
}
