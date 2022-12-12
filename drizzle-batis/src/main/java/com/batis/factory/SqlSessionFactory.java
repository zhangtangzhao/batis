package com.batis.factory;

import com.batis.api.SqlSession;
import com.batis.api.SqlSessionImpl;
import com.batis.entity.Configuration;

public class SqlSessionFactory {

    private Configuration configuration;

    public SqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    public SqlSession openSession() {
        return new SqlSessionImpl(configuration);
    }
}
