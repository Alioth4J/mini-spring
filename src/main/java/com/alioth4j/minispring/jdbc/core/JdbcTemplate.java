package com.alioth4j.minispring.jdbc.core;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

public class JdbcTemplate {

    private DataSource dataSource;

    public JdbcTemplate() {
    }

    public Object query(StatementCallback stmtCallback) {
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        Object rtnObject = null;

        try {
            con = dataSource.getConnection();
            stmt = con.createStatement();

            // callback
            return stmtCallback.doInStatement(stmt);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
                stmt.close();
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return rtnObject;
    }

    public Object query(String sql, Object[] args, PreparedStatementCallback pstmtcallback) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Object rtnObject = null;

        try {
            con = dataSource.getConnection();
            pstmt = con.prepareStatement(sql);

            // 设置参数
            ArgumentPreparedStatementSetter argumentSetter = new ArgumentPreparedStatementSetter(args);
            argumentSetter.setValues(pstmt);

            // callback
            return pstmtcallback.doInPreparedStatement(pstmt);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                pstmt.close();
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public <T> List<T> query(String sql, Object[] args, RowMapper<T> rowMapper) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Object rtnObject = null;
        RowMapperResultSetExtractor<T> resultExtractor = new RowMapperResultSetExtractor<>(rowMapper);

        try {
            con = dataSource.getConnection();
            pstmt = con.prepareStatement(sql);

            // 设置参数
            ArgumentPreparedStatementSetter argumentSetter = new ArgumentPreparedStatementSetter(args);
            argumentSetter.setValues(pstmt);

            // 处理返回结果
            return resultExtractor.extractData(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                pstmt.close();
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

//    protected abstract Object doInStatement(ResultSet rs);

}
