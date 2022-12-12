package com.batis.configure;

import com.batis.factory.SqlSessionFactory;
import com.batis.factory.SqlSessionFactoryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import java.io.InputStream;

@Configurable
@Import(BatisProperties.class)
@ConditionalOnClass(name = "com.batis.factory.SqlSessionFactory")
public class BatisAutoConfiguration {

    @Autowired
    private  BatisProperties batisProperties;

    /**
     * 注入核心对象到Spring的容器中
     * @return
     */
    @Bean
    @ConditionalOnProperty(name = "batis.properties.enable", havingValue = "false", matchIfMissing=true)
    public SqlSessionFactory sqlSessionFactoryXml(){
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

    @Bean
    @ConditionalOnProperty(name = "batis.properties.enable", havingValue = "true", matchIfMissing=true)
    public SqlSessionFactory sqlSessionFactoryProperties(){
        SqlSessionFactory sqlSessionFactory = null;
        try {
            SqlSessionFactoryBuilder builder = new  SqlSessionFactoryBuilder();
            sqlSessionFactory = builder.buildProperties(batisProperties.getDriver(), batisProperties.getUrl(),
                    batisProperties.getPassword(), batisProperties.getUsername(), batisProperties.getMappers());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sqlSessionFactory;
    }

}
