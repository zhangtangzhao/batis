package com.drizzle.factory;

import com.drizzle.entity.Configuration;
import com.drizzle.entity.SqlSource;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class SqlSessionFactoryBuilder {

    public SqlSessionFactory build(InputStream inputStream) throws Exception {
        Configuration configuration = new Configuration();
        loadXmlConfig(configuration, inputStream);
        return new SqlSessionFactory(configuration);
    }

    private void loadXmlConfig(Configuration configuration, InputStream inputStream) throws Exception{
        SAXReader saxReader = new SAXReader();
        Document document = saxReader.read(inputStream);
        List<Element> nodes = document.selectNodes("//property");

        for (Element element : nodes) {
            String name = element.attributeValue("name");
            if ("driver".equals(name)) {
                configuration.setDriver(element.attributeValue("value"));
            } else if ("url".equals(name)) {
                configuration.setUrl(element.attributeValue("value"));
            } else if ("username".equals(name)) {
                configuration.setUsername(element.attributeValue("value"));
            } else if ("password".equals(name)) {
                configuration.setPassword(element.attributeValue("value"));
            }
        }
        
        List<Element> mappers = document.selectNodes("//mapper");
        for (Element element : mappers) {
            String resource = element.attributeValue("resource");
            loadSqlConfig(resource, configuration);
        }
        
    }

    private void loadSqlConfig(String resource, Configuration configuration) throws Exception{
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(resource);
        SAXReader saxReader = new SAXReader();
        Document document = saxReader.read(inputStream);
        Element root = document.getRootElement();
        String namespace = root.attributeValue("namespace");
        List<Element> nodes = document.selectNodes("//select");
        for (Element element : nodes) {
            String id = element.attributeValue("id");
            String resultType = element.attributeValue("resultType");
            String sql = element.getText();
            SqlSource sqlSource = new SqlSource();
            sqlSource.setSql(sql);
            sqlSource.setResultType(resultType);
            configuration.getSqlSourceMap().put(namespace +"." + id, sqlSource);
        }
    }
}
