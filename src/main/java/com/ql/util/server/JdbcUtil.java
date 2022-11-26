package com.ql.util.server;

import java.sql.*;

public class JdbcUtil {
    private static JdbcUtil jdbcUtil=null;
    private JdbcUtil(){
    }
    public static JdbcUtil getJdbcUtil(){
        if(jdbcUtil == null){
            jdbcUtil = new JdbcUtil();
        }
        return jdbcUtil;
    }
    //驱动
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() throws SQLException{
        String url = PropertiesUtil.getPropertiesUtil().getValue("url").trim();
        String user = PropertiesUtil.getPropertiesUtil().getValue("user").trim();
        String passwd = PropertiesUtil.getPropertiesUtil().getValue("password").trim();
        return DriverManager.getConnection(url,user,passwd);
    }
    public void closeConnection(ResultSet resultSet, PreparedStatement preparedStatement, Connection connection) throws SQLException{
        try {
            if(resultSet!=null)
                resultSet.close();
            if(preparedStatement!=null)
                preparedStatement.close();
            if(connection!=null)
                connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
