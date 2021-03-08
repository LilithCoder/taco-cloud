package tacocloud.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * @Repository是 Spring 定义的少数几个原型注解之一
 * 通过使用 @Repository 对 JdbcIngredientRepository 进行注解，
 * 这样它就会由 Spring 组件在扫描时自动发现，并在 Spring 应用程序上下文中生成 bean 实例
 * */
@Repository
public class JdbcIngredientRepository implements IngredientRepository {
    private JdbcTemplate jdbc;

    @Autowired // 通过 @Autowired 注解将 JdbcTemplate 注入到 JdbcIngredientRepository bean 中
    public JdbcIngredientRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc; // 该变量将在其他方法中用于查询和插入数据库
    }


}