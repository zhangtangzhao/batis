package com.drizzle;

import com.drizzle.api.SqlSession;
import com.drizzle.entity.User;
import com.drizzle.factory.SqlSessionFactory;
import com.drizzle.factory.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;

public class BatisTest {

    @Test
    public void get()  throws  Exception {
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        InputStream inputStream = this.getClass().getResourceAsStream("SqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = builder.build(inputStream);

        SqlSession sqlSession = sqlSessionFactory.openSession();

        List<User> users = sqlSession.selectList("com.drizzle.dao.UserMapper.findAll");

        for (User user : users) {
            System.out.println(user);
        }
    }
}
