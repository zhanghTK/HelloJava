package tk.zhangh.java.x.server;

import lombok.Getter;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * 解析web.xml
 * Created by ZhangHao on 2016/6/8.
 */
public class WebXmlHandler extends DefaultHandler {
    @Getter
    private List<Entity> entityList;
    @Getter
    private List<Mapping> mappingList;
    private Entity entity;
    private Mapping mapping;
    private String beginTag;
    private boolean isMap;

    @Override
    public void startDocument() throws SAXException {
        entityList = new ArrayList<>();
        mappingList = new ArrayList<>();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName == null) {
            return;
        }
        beginTag = qName;
        if ("servlet".equals(qName)) {
            isMap = false;
            entity = new Entity();
        }
        if ("servlet-mapping".equals(qName)) {
            isMap = true;
            mapping = new Mapping();
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (beginTag == null) {
            return;
        }
        String value = new String(ch, start, length);
        if (isMap) {
            if (beginTag.equals("servlet-name")) {
                mapping.setName(value);
            }
            if (beginTag.equals("url-pattern")) {
                mapping.getUrlPattern().add(value);
            }
        }
        if (!isMap) {
            if (beginTag.equals("servlet-name")) {
                entity.setName(value);
            }
            if (beginTag.equals("servlet-class")) {
                entity.setClazz(value);
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if ("servlet".equals(qName)) {
            entityList.add(entity);
        }
        if ("servlet-mapping".equals(qName)) {
            mappingList.add(mapping);
        }
        beginTag = null;
    }
}
