package com.batis.api;

import java.util.List;

public interface SqlSession {

    <T> List<T> selectList(String statement) throws Exception;

}
