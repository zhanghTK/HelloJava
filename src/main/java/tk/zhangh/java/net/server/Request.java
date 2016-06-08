package tk.zhangh.java.net.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.URLDecoder;
import java.util.*;

/**
 * Created by ZhangHao on 2016/6/7.
 */
public class Request {
    private static final String CRLF = "\r\n";
    private String method;
    private String url;
    private Map<String, List<String>> parameterMapValues;
//    private InputStream inputStream;
    private String requestInfo;

    public Request(Socket client)throws Exception{
        method = "";
        url = "";
        parameterMapValues = new HashMap<String, List<String>>();
        requestInfo = "";
//        this.inputStream = inputStream;
        byte[] data = new byte[20480];
        try {
            InputStream inputStream = client.getInputStream();
            int length = inputStream.read(data);
            requestInfo = new String(data, 0, length);
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
        parameterMapValues();
    }

    private void parameterMapValues(){
        if (requestInfo == null || requestInfo.trim().equals("")){
            return;
        }

        String paramString = "";
        String firstLine = requestInfo.substring(0, requestInfo.indexOf(CRLF));
        method = firstLine.substring(0, firstLine.indexOf("/")).trim();
        String urlStr = firstLine.substring(firstLine.indexOf("/"), firstLine.indexOf("HTTP/")).trim();
        if (method.equalsIgnoreCase("post")){
            url = urlStr;
            paramString = requestInfo.substring(requestInfo.lastIndexOf(CRLF)).trim();
        }else if (method.equalsIgnoreCase("get")){
            if (urlStr.contains("?")){
                String[] urlArr = urlStr.split("\\?");
                url = urlArr[0];
                paramString = urlArr[1];
            }else {
                url = urlStr;
            }
        }
        if (paramString==null || paramString.equals("")){
            return;
        }
        parseParams(paramString);
    }

    private void parseParams(String paramString){
        StringTokenizer tokenizer = new StringTokenizer(paramString, "&");
        while (tokenizer.hasMoreTokens()){
            String keyValue = tokenizer.nextToken();
            String[] keyValues = keyValue.split("=");
            if (keyValues!= null && keyValues.length == 1){
                keyValues = Arrays.copyOf(keyValues, 2);
                keyValues[1] = null;
            }
            String key = keyValues[0].trim();
            String value = null == keyValues[1] ? null : decode(keyValues[1].trim(), "utf-8");
            if (!parameterMapValues.containsKey(key)){
                parameterMapValues.put(key, new ArrayList<String>());
            }
            List<String> values = parameterMapValues.get(key);
            values.add(value);
        }
        System.out.println("");
    }

    public String getParameter(String name){
        String[] values = getParameterValues(name);
        if (values == null){
            return null;
        }else {
            return values[0];
        }
    }

    public String[] getParameterValues(String name){
        List<String> values = null;
        if ((values = parameterMapValues.get(name)) == null){
            return null;
        }else {
            return values.toArray(new String[0]);
        }
    }

    private String decode(String value, String code){
        String result = null;
        try {
            result = URLDecoder.decode(value, code);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return  result;
    }

    public String getUrl() {
        return url;
    }
}
