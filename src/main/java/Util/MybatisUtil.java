package Util;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class MybatisUtil {
    MybatisUtil() {
    }

    private static SqlSessionFactory sqlSessionFactory;

    /*创建会话工厂*/
    static {
        //获取mybatis全局配置文件
        String resource = "mybatis-config.xml";
        InputStream inputStream = null;
        try {
            inputStream = Resources.getResourceAsStream(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //读取配置文件的配置信息，利用SqlSessionFactoryBuilder创建sqlSessionFactory
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    }

    /**
     * 获取SqlSession回话
     */
    public static SqlSession getSqlSession() {
        return sqlSessionFactory.openSession();
    }
}
