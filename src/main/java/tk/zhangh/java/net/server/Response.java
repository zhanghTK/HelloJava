package tk.zhangh.java.net.server;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Date;

/**
 * Created by ZhangHao on 2016/6/7.
 */
public class Response {
    private static final String CRLF = "\r\n";  // 换行符常量
    private static final String BLANK = " ";  // 空格字符常量
    private StringBuilder headInfo;  // 响应头
    private StringBuilder content;  // 响应正文
//    private int length;  // 响应正文长度
    private BufferedWriter bufferedWriter;  // 输出流


    public Response(Socket client)throws Exception{
        headInfo = new StringBuilder();
        content = new StringBuilder();
//        length = 0;
        try {
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
        } catch (IOException e) {
            headInfo = null;
            throw e;
        }
    }

    public Response(OutputStream outputStream){
        headInfo = new StringBuilder();
        content = new StringBuilder();
//        length = 0;
        bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
    }

    /**
     * 构建正文
     * @param info
     * @return
     */
    public Response print(String info){
        content.append(info);
//        length += info.getBytes().length;
        return this;
    }

    /**
     * 构建正文，带换行符
     * @param info
     * @return
     */
    public Response println(String info){
        info += CRLF;
        return print(info);
    }

    /**
     * 构建响应头
     * @param code
     */
    private void createHeadInfo(int code){
        headInfo.append("HTTP/1.1").append(BLANK).append(code).append(BLANK);
        switch (code){
            case 200:
                headInfo.append("OK");
                break;
            case 404:
                headInfo.append("NOT FOUND");
                break;
            case 500:
                headInfo.append("SERVER ERROR");
        }
        headInfo.append(CRLF);
        headInfo.append("Server:zhanghTK Server/0.0.1").append(CRLF);
        headInfo.append("Date:").append(new Date()).append(CRLF);
        headInfo.append("Content-Type:text/html;charset=utf-8").append(CRLF);
        headInfo.append("Content-Length:").append(content.toString().getBytes()).append(CRLF);
        headInfo.append(CRLF);
    }

    /**
     *
     * @param code
     * @throws IOException
     */
    void pushToClient(int code)throws IOException{
        if (headInfo == null){
            code = 500;
        }
        createHeadInfo(code);
        bufferedWriter.append(headInfo.toString());
        bufferedWriter.append(content.toString());
        bufferedWriter.flush();
        bufferedWriter.close();
    }

    public void close(){
        CloseUtil.closeAll(bufferedWriter);
    }
}
