package tk.zhangh.java.x.server;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * servlet管理
 * Created by ZhangHao on 2016/6/7.
 */
public class WebApp {
    private static ServletContext context;

    static {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            System.out.println();
            SAXParser saxParser = factory.newSAXParser();
            WebXmlHandler webXmlHandler = new WebXmlHandler();
            saxParser.parse(
                    Thread.currentThread().getContextClassLoader()
                            .getResourceAsStream("web.xml")
                    , webXmlHandler);
            context = new ServletContext();
            Map<String, String> servletMapping = context.getServletMapping();

            // servletClass->servletName
            for (Entity entity : webXmlHandler.getEntityList()) {
                servletMapping.put(entity.getName(), entity.getClazz());
            }
            // urlPattern->servletName
            Map<String, String> urlMapping = context.getUrlMapping();
            for (Mapping mapping : webXmlHandler.getMappingList()) {
                List<String> urls = mapping.getUrlPattern();
                for (String url : urls) {
                    urlMapping.put(url, mapping.getName());
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }

    public static Servlet getServlet(String url) {
        if (url == null || (url = url.trim()).equals("")) {
            return null;
        }
        String className = context.getServletMapping().get(context.getUrlMapping().get(url));
        try {
            Class<?> clazz = Class.forName(className);
            return (Servlet) clazz.newInstance();
        } catch (Exception e) {
            return null;
        }
    }
}
