package com.alioth4j.minispring.jdbc.core;

import java.sql.Statement;
import java.sql.SQLException;

@FunctionalInterface
public interface StatementCallback {

    Object doInStatement(Statement stmt) throws SQLException;

}
