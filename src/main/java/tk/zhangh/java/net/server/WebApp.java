package tk.zhangh.java.net.server;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by ZhangHao on 2016/6/7.
 */
public class WebApp {
    private static ServletContext context;
    static {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            WebHandler webHandler = new WebHandler();
            saxParser.parse(
                    Thread.currentThread().getContextClassLoader()
                            .getResourceAsStream("tk/zhangh/java/net/server/web.xml")
                    , webHandler);
            context = new ServletContext();
            Map<String, String> servletMapping = context.getServletMapping();

            // servletClass->servletName
            for (Entity entity : webHandler.getEntityList()){
                servletMapping.put(entity.getName(), entity.getClz());
            }
            // urlPattern->servletName
            Map<String, String> urlMapping = context.getUrlMapping();
            for (Mapping mapping : webHandler.getMappingList()){
                List<String> urls = mapping.getUrlPattern();
                for (String url : urls){
                    urlMapping.put(url, mapping.getName());
                }
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Servlet getServlet(String url){
        if (url == null || (url=url.trim()).equals("")){
            return null;
        }
        String className = context.getServletMapping().get(context.getUrlMapping().get(url));
        try {
                Class<?> clazz = Class.forName(className);
                Servlet servlet = (Servlet)clazz.newInstance();
                return servlet;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
