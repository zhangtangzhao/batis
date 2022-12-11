package com.batis;


import com.batis.entity.Configuration;
import com.batis.entity.SqlSource;

import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Executor {

    private Configuration configuration;

    public Executor(Configuration configuration) {
        this.configuration = configuration;
    }

    public List executeQuery(String statement) throws Exception {
        String driver = configuration.getDriver();
        String url = configuration.getUrl();
        String username = configuration.getUsername();
        String password = configuration.getPassword();

        Map<String, SqlSource> map = configuration.getSqlSourceMap();

        SqlSource sqlSource = map.get(statement);
        String sql =  sqlSource.getSql();
        String resultType = sqlSource.getResultType();

        Class.forName(driver);

        Connection connection = DriverManager.getConnection(url, username, password);
        PreparedStatement ps = connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();
        List<String> columnNames = new ArrayList<>();
        for (int i = 0; i < columnCount; i++) {
            columnNames.add(metaData.getColumnName(i));
        }

        List list = new ArrayList();
        while (rs.next()) {
            Class<?> clazz = Class.forName(resultType);
            Object user = clazz.newInstance();
            Method[] methods = clazz.getMethods();
            for (String columnName : columnNames) {
                for (Method method : methods) {
                    String methodName = method.getName();
                    if (("set"+columnName).equalsIgnoreCase(methodName)) {
                        method.invoke(user, rs.getObject(columnName));
                    }
                }
            }
            list.add(user);
        }
        rs.close();
        ps.close();
        connection.close();
        return list;
    }
}