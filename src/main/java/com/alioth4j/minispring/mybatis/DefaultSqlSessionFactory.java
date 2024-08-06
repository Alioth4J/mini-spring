package com.alioth4j.minispring.mybatis;

import com.alioth4j.minispring.beans.factory.annotation.Autowired;
import com.alioth4j.minispring.jdbc.core.JdbcTemplate;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class DefaultSqlSessionFactory implements SqlSessionFactory {

    @Autowired
    JdbcTemplate jdbcTemplate;

    String mapperLocations;

    Map<String, MapperNode> mapperNodeMap = new HashMap<>();

    public void init() {
        scanLocation(this.mapperLocations);
    }

    private void scanLocation(String mapperLocation) {
        String sLocationPath = this.getClass().getClassLoader().getResource("").getPath() + mapperLocation;
        File dir = new File(sLocationPath);
        for (File file : dir.listFiles()) {
            if (file.isDirectory()) {
                scanLocation(mapperLocation + file.getName());
            } else {
                buildMapperNodes(mapperLocation + "/" + file.getName());
            }
        }
    }

    private Map<String, MapperNode> buildMapperNodes(String filePath) {
        SAXReader saxReader = new SAXReader();
        URL xmlPath = this.getClass().getClassLoader().getResource(filePath);
        try {
            Document document = saxReader.read(xmlPath);
            Element rootElement = document.getRootElement();
            String namespace = rootElement.attributeValue("namespace");
            Iterator<Element> nodes = rootElement.elementIterator();
            while (nodes.hasNext()) {
                Element node = nodes.next();
                String id = node.attributeValue("id");
                String parameterType = node.attributeValue("parameterType");
                String resultType = node.attributeValue("resultType");
                String sql = node.getText();

                MapperNode selectNode = new MapperNode();
                selectNode.setNamespace(namespace);
                selectNode.setId(id);
                selectNode.setParameterType(parameterType);
                selectNode.setResultType(resultType);
                selectNode.setSql(sql);
                selectNode.setParameter("");

                this.mapperNodeMap.put(namespace + "." + id, selectNode);
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return this.mapperNodeMap;
    }

    @Override
    public SqlSession openSession() {
        DefaultSqlSession newSqlSession = new DefaultSqlSession();
        newSqlSession.setJdbcTemplate(this.jdbcTemplate);
        newSqlSession.setSqlSessionFactory(this);
        return newSqlSession;
    }

    @Override
    public MapperNode getMapperNode(String name) {
        return this.mapperNodeMap.get(name);
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public String getMapperLocations() {
        return mapperLocations;
    }

    public void setMapperLocations(String mapperLocations) {
        this.mapperLocations = mapperLocations;
    }

    public Map<String, MapperNode> getMapperNodeMap() {
        return mapperNodeMap;
    }

    public void setMapperNodeMap(Map<String, MapperNode> mapperNodeMap) {
        this.mapperNodeMap = mapperNodeMap;
    }
}
