package com.drizzle.api;

import com.drizzle.Executor;
import com.drizzle.entity.Configuration;

import java.util.List;

public class SqlSessionImpl implements SqlSession{

    private Configuration configuration;

    public SqlSessionImpl(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public <T> List<T> selectList(String statement) throws Exception {
        Executor executor = new Executor(configuration);
        return (List<T>) executor.executeQuery(statement);
    }
}
