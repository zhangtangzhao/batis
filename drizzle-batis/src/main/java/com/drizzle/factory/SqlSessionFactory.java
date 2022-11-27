package com.drizzle.factory;

import com.drizzle.api.SqlSession;
import com.drizzle.api.SqlSessionImpl;
import com.drizzle.entity.Configuration;

public class SqlSessionFactory {

    private Configuration configuration;

    public SqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    public SqlSession openSession() {
        return new SqlSessionImpl(configuration);
    }
}
