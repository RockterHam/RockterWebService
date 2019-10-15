package com.rockter.server.basic;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class XmlTest {
    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
        //获取解析工厂
        SAXParserFactory factory = SAXParserFactory.newInstance();
        //从解析工厂获取解析器
        SAXParser parser = factory.newSAXParser();
        //加载文档Document注册处理器
        //编写处理器
        PersonHandler handler = new PersonHandler();
        parser.parse(Thread.currentThread().getContextClassLoader().getResourceAsStream("com/rockter/server/basic/p.xml"),
                handler);

        //获取数据
        List<Person> personList = handler.getPersons();
        for (var temp:personList){
            System.out.println("Age:" + temp.getAge() + "|" + "Name:" + temp.getName());
        }
    }
}

class PersonHandler extends DefaultHandler{
    private List<Person> persons;
    private Person person;
    private String tag;

    @Override
    public void startDocument() throws SAXException {
        persons = new ArrayList<Person>();
        System.out.println("----解析文档开始----");
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        System.out.println("start--"+qName);
        if (null != qName){
            tag = qName;
            if (tag.equals("person")){
                person = new Person();
                System.out.println("开始解析Person");
            }
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String contents = new String(ch,start,length).trim();
        System.out.println("char--"+contents);
        if (null != tag){
            if (tag.equals("name")){
                System.out.println(contents);
                person.setName(contents);
            }else

                if (tag.equals("age")){
                    System.out.println(Integer.valueOf(contents));
                    person.setAge(Integer.valueOf(contents));
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        System.out.println("end--"+qName);
        if (qName != null && qName.equals("person")){
            System.out.println("person放入persons");
            persons.add(person);
        }
        tag = null;
    }

    @Override
    public void endDocument() throws SAXException {
        System.out.println("----解析文档结束----");
    }

    public List<Person> getPersons() {
        return persons;
    }
}
