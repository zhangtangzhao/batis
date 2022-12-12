package com.test;

import com.batis.api.SqlSession;
import com.batis.factory.SqlSessionFactory;
import com.test.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest(classes = {Application.class})
@RunWith(SpringRunner.class)
public class BatisTest {

    @Autowired
    private SqlSessionFactory sqlSessionFactory;



    @Test
    public void get()  throws  Exception {

        SqlSession sqlSession = sqlSessionFactory.openSession();

        List<User> users = sqlSession.selectList("com.test.dao.UserMapper.findAll");

        for (User user : users) {
            System.out.println(user);
        }
    }
}
