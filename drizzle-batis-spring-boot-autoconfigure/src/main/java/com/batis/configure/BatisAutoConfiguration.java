package com.batis.configure;

import com.batis.factory.SqlSessionFactory;
import com.batis.factory.SqlSessionFactoryBuilder;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import java.io.InputStream;

@Configurable
@Import(BatisProperties.class)
@ConditionalOnClass(name = "com.batis.factory.SqlSessionFactory")
public class BatisAutoConfiguration {

    /**
     * 注入核心对象到Spring的容器中
     * @return
     */
    @Bean
//    @ConditionalOnMissingBean(SqlSessionFactory.class)
    public SqlSessionFactory initFactory(){
        SqlSessionFactory sqlSessionFactory = null;
        try {
            SqlSessionFactoryBuilder builder = new  SqlSessionFactoryBuilder();
            InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("SqlMapConfig.xml");
            sqlSessionFactory = builder.build(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sqlSessionFactory;
    }

}
