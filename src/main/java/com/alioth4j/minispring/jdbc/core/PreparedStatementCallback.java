package com.alioth4j.minispring.jdbc.core;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@FunctionalInterface
public class PreparedStatementCallback {

    Object doInPreparedStatement(PreparedStatement stmt) throws SQLException;

}
