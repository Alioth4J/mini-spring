package com.alioth4j.minispring.mybatis;

import com.alioth4j.minispring.jdbc.core.JdbcTemplate;
import com.alioth4j.minispring.jdbc.core.PreparedStatementCallback;

public interface SqlSession {

    void setJdbcTemplate(JdbcTemplate jdbcTemplate);
    void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory);
    Object selectOne(String sqlid, Object[] args, PreparedStatementCallback callback);

}
