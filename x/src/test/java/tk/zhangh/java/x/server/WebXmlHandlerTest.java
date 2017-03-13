package tk.zhangh.java.x.server;

import org.junit.Assert;
import org.junit.Test;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Created by ZhangHao on 2017/3/13.
 */
public class WebXmlHandlerTest {


    @Test
    public void test() throws Exception {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();
        WebXmlHandler webXmlHandler = new WebXmlHandler();
        saxParser.parse(Thread.currentThread().getContextClassLoader().getResourceAsStream("web.xml"), webXmlHandler);
        Assert.assertNotNull(webXmlHandler.getEntityList());
    }
}