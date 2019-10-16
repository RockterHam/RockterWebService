package com.rockter.server.basic.servlet;

import com.rockter.server.basic.Person;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;

public class XmlRead {
    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        //获取解析工厂
        SAXParserFactory factory = SAXParserFactory.newInstance();
        //从解析工厂获取解析器
        SAXParser parser = factory.newSAXParser();
        //加载文档Document注册处理器
        //编写处理器
        WebHandler handler = new WebHandler();

        WebContext context = new WebContext(handler.getEntitys(),handler.getMappings());
        String className = context.getClz("/g");
        Class clz = Class.forName(className);
        Servlet servlet = (Servlet) clz.getConstructor().newInstance();
        servlet.service();
        //获取数据
//        List<Entity> EntityList = handler.getEntitys();
//        List<Mapping> mappingList = handler.getMappings();
//
//        for (var temp:EntityList){
//            System.out.println("Class:" + temp.getClz() + "<-------->" + "Name:" + temp.getName());
//        }
//        System.out.print("\n");
//        for (var temp:mappingList){
//            System.out.println("Patterns:" + temp.getPatterns() + "<-------->" + "Name:" + temp.getName());
//        }
    }
}

class WebHandler extends DefaultHandler{
    private List<Entity> entitys;
    private List<Mapping> mappings;
    private Entity entity;
    private Mapping mapping;
    private String tag;
    private boolean isMapping = false;

    @Override
    public void startDocument() throws SAXException {
        entitys = new ArrayList<>();
        mappings = new ArrayList<>();

    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (null != qName){
            tag = qName;
            if (tag.equals("servlet")){
                entity = new Entity();
                isMapping = false;
            }else if (tag.equals("servlet-mapping")){
                mapping = new Mapping();
                isMapping = true;
            }
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String contents = new String(ch,start,length).trim();
        if (null != tag && isMapping == false){
            if (tag.equals("servlet-name")){
                entity.setName(contents);
            }else

                if (tag.equals("servlet-class")){
                    entity.setClz(contents);
            }
        }else if (null != tag && isMapping){
            if (tag.equals("servlet-name")){
                mapping.setName(contents);
            }else

            if (tag.equals("url-pattern")){
                mapping.addPatterns(contents);
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName != null && qName.equals("servlet")){
            entitys.add(entity);
        }else if (qName != null && qName.equals("servlet-mapping")){
            mappings.add(mapping);
        }
        tag = null;
    }

    public List<Entity> getEntitys() {
        return entitys;
    }

    public List<Mapping> getMappings() {
        return mappings;
    }
}
