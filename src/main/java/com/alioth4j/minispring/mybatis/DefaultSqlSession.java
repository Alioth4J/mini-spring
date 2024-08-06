package com.alioth4j.minispring.mybatis;

import com.alioth4j.minispring.jdbc.core.JdbcTemplate;
import com.alioth4j.minispring.jdbc.core.PreparedStatementCallback;

import java.sql.ResultSet;

public class DefaultSqlSession implements SqlSession{

    JdbcTemplate jdbcTemplate;
    SqlSessionFactory sqlSessionFactory;

    @Override
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    @Override
    public Object selectOne(String sqlid, Object[] args, PreparedStatementCallback callback) {
        String sql = this.sqlSessionFactory.getMapperNode(sqlid).getSql();
        return jdbcTemplate.query(sql, args, callback);
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public SqlSessionFactory getSqlSessionFactory() {
        return sqlSessionFactory;
    }

    private void buildParameter() {
    }

    private Object resultSet2Obj(ResultSet rs) {
        return null;
    }

}
