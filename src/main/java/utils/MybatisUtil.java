package utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.exceptions.PersistenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Reader;
import java.util.function.Consumer;
import java.util.function.Function;

public class MybatisUtil {

    private static final Logger logger = LoggerFactory.getLogger(MybatisUtil.class);
    private static final SqlSessionFactory sqlSessionFactory;

    static {
        try (Reader reader = Resources.getResourceAsReader("mybatis-config.xml")) {
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        } catch (IOException e) {
            logger.error("Failed to initialize Mybatis SqlSessionFactory", e);
            throw new RuntimeException("Failed to initialize Mybatis SqlSessionFactory", e);
        }
    }

    public static SqlSession getSqlSession(boolean autoCommit) {
        return sqlSessionFactory.openSession(autoCommit);
    }

    // 通用执行器方法，传递一个 Function 并指定是否自动提交
    public static <R> R execute(Function<SqlSession, R> function, boolean autoCommit) {
        try (SqlSession sqlSession = getSqlSession(autoCommit)) {
            try {
                R result = function.apply(sqlSession);
                if (!autoCommit) {
                    sqlSession.commit();
                }
                return result;
            } catch (PersistenceException e) {
                if (!autoCommit) {
                    sqlSession.rollback();
                }
                logger.error("Error executing function", e);
                throw e;
            }
        }
    }

    // 通用执行器方法，传递一个 Consumer 并指定是否自动提交
    public static void execute(Consumer<SqlSession> consumer, boolean autoCommit) {
        try (SqlSession sqlSession = getSqlSession(autoCommit)) {
            try {
                consumer.accept(sqlSession);
                if (!autoCommit) {
                    sqlSession.commit();
                }
            } catch (PersistenceException e) {
                if (!autoCommit) {
                    sqlSession.rollback();
                }
                logger.error("Error executing consumer", e);
                throw e;
            }
        }
    }

    // 默认自动提交的通用执行器方法，传递一个 Function
    public static <R> R execute(Function<SqlSession, R> function) {
        return execute(function, true);
    }

    // 默认自动提交的通用执行器方法，传递一个 Consumer
    public static void execute(Consumer<SqlSession> consumer) {
        execute(consumer, true);
    }
}
