package com.alioth4j.minispring.mybatis;

public interface SqlSessionFactory {

    SqlSession openSession();
    MapperNode getMapperNode(String name);

}
