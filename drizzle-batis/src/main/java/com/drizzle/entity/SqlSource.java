package com.drizzle.entity;

public class SqlSource {

    // 存储sql语句
    private String sql;

    // 存储JavaBean对象类的全限定名称
    private String resultType;

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }
}
