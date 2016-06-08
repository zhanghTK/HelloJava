package tk.zhangh.java.net.server;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZhangHao on 2016/6/8.
 */
public class WebHandler extends DefaultHandler{
    private List<Entity> entityList;
    private List<Mapping> mappingList;
    private Entity entity;
    private Mapping mapping;
    private String beginTag;
    private boolean isMap;

    @Override
    public void startDocument() throws SAXException {
        // 文档解析开始
        entityList = new ArrayList<Entity>();
        mappingList = new ArrayList<Mapping>();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        // 元素解析开始
        if (qName != null){
            beginTag = qName;
            if (qName.equals("servlet")){
                isMap = false;
                entity = new Entity();
            }else if (qName.equals("servlet-mapping")){
                isMap = true;
                mapping = new Mapping();
            }
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        // 解析内容
        if (beginTag != null){
            String value = new String(ch, start, length);
            if (isMap){
                if (beginTag.equals("servlet-name")){
                    mapping.setName(value);
                }else if (beginTag.equals("url-pattern")){
                    mapping.getUrlPattern().add(value);
                }
            }else {
                if (beginTag.equals("servlet-name")){
                    entity.setName(value);
                }else if (beginTag.equals("servlet-class")){
                    entity.setClz(value);
                }
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        // 元素解析结束
        if (qName != null){
            if (qName.equals("servlet")){
                entityList.add(entity);
            }else if (qName.equals("servlet-mapping")){
                mappingList.add(mapping);
            }
        }
        beginTag = null;
    }

    @Override
    public void endDocument() throws SAXException {
        // 文档解析结束
    }

    public List<Entity> getEntityList() {
        return entityList;
    }

    public List<Mapping> getMappingList() {
        return mappingList;
    }

    public static void main(String[] args) throws Exception{
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();
        WebHandler webHandler = new WebHandler();
        saxParser.parse(
                Thread.currentThread().getContextClassLoader()
                        .getResourceAsStream("tk/zhangh/java/net/server/web.xml")
                , webHandler);
        System.out.println(webHandler.getEntityList());
    }
}
