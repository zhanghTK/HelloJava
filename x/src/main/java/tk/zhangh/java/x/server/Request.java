package tk.zhangh.java.x.server;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tk.zhangh.java.x.server.exception.RequestInitException;

import java.io.IOException;
import java.net.Socket;
import java.util.*;

import static tk.zhangh.java.x.server.ServerUtils.CRLF;

/**
 * 请求
 * Created by ZhangHao on 2017/2/22.
 */
public class Request {
    private final String requestContent;
    @Getter
    private final String method;
    @Getter
    private final String url;
    private final Map<String, List<String>> parameterValues;
    private Logger logger = LoggerFactory.getLogger(Request.class);


    public Request(Socket client) {
        try {
            byte[] data = new byte[20480];
            int length = client.getInputStream().read(data);
            requestContent = new String(data, 0, length, "utf-8");
            logger.info("get request content:{}", requestContent);
            method = parseMethod(requestContent);
            url = parseUrl(requestContent);
            parameterValues = new HashMap<>();
            parameterMapValues();
        } catch (IOException e) {
            throw new RequestInitException();
        }
    }

    private String parseMethod(String requestContent) {
        return requestContent.substring(0, requestContent.indexOf("/")).trim();
    }

    private String parseCompleteUrl(String requestContent) {
        return requestContent.substring(requestContent.indexOf("/"), requestContent.indexOf("HTTP/")).trim();
    }

    private String parseUrl(String requestContent) {
        return parseCompleteUrl(requestContent).split("\\?")[0];
    }

    private String getParamStr(String requestContent, String method) {
        if (HttpMethod.POST.name().equalsIgnoreCase(method)) {
            return requestContent.substring(requestContent.lastIndexOf(CRLF)).trim();
        }
        if (HttpMethod.GET.name().equalsIgnoreCase(method)) {
            if (parseCompleteUrl(requestContent).split("\\?").length > 1) {
                return parseCompleteUrl(requestContent).split("\\?")[1];
            }
        }
        return "";
    }


    private void parameterMapValues() {
        if (requestContent == null || requestContent.trim().equals("")) {
            return;
        }
        String paramString = getParamStr(requestContent, method);
        if (paramString == null || paramString.equals("")) {
            return;
        }
        addParams(paramString);
    }

    private void addParams(String paramString) {
        StringTokenizer tokenizer = new StringTokenizer(paramString, "&");
        while (tokenizer.hasMoreTokens()) {
            String keyValue = tokenizer.nextToken();
            String[] keyValues = keyValue.split("=");
            if (keyValues.length == 1) {
                keyValues = Arrays.copyOf(keyValues, 2);
                keyValues[1] = null;
            }
            String key = keyValues[0].trim();
            String value = (null == keyValues[1] ? null : ServerUtils.decode(keyValues[1].trim()));
            if (!parameterValues.containsKey(key)) {
                parameterValues.put(key, new ArrayList<>());
            }
            parameterValues.get(key).add(value);
        }
    }

    public String getParameter(String name) {
        String[] values = getParameterValues(name);
        if (values == null) {
            return null;
        } else {
            return values[0];
        }
    }

    public String[] getParameterValues(String name) {
        List<String> values;
        if ((values = parameterValues.get(name)) == null) {
            return null;
        } else return values.toArray(new String[values.size()]);
    }
}
