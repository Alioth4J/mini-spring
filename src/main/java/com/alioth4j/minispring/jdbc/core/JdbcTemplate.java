package com.alioth4j.minispring.jdbc.core;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Date;

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
            for (int i = 0; i < args.length; i++) {
                Object arg = args[i];
                // 按不同的数据类型调用相应的方法进行设置
                if (arg instanceof String) {
                    pstmt.setString(i + 1, (String)arg);
                } else if (arg instanceof Integer) {
                    pstmt.setInt(i + 1, (int)arg);
                } else if (arg instanceof Date) {
                    pstmt.setDate(i + 1, new Date((Date)arg).getTime());
                }
            }
            // callback
            return pstmtcallback.doInPreparedStatement(pstmt);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
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
