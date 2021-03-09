package tacocloud.data;

import org.omg.PortableInterceptor.INACTIVE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import tacocloud.Ingredient;

import java.sql.ResultSet;
import java.sql.SQLException;

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

    /**
     * 返回所有成分对象
     * */
    @Override
    public Iterable<Ingredient> findAll() {
        /**
         * query() 方法接受查询的 SQL 以及 Spring 的 RowMapper 实现，
         * 以便将结果集中的每一行映射到一个对象
         * */
        return jdbc.query("select id, name, type from Ingredient",
                this::mapRowToIngredient);
    }

    /**
     * 返回单个成分对象
     * */
    @Override
    public Ingredient findOne(String id) {
        return jdbc.queryForObject(
                "select id, name, type from Ingredient where id=?",
                this::mapRowToIngredient, id);
    }

    /**
     * RowMapper
     * 将结果集中的每一行映射到一个对象
     * */
    private Ingredient mapRowToIngredient(ResultSet rs, int rowNum)
            throws SQLException {
        return new Ingredient(
                rs.getString("id"),
                rs.getString("name"),
                Ingredient.Type.valueOf(rs.getString("type")));
    }

    /**
     * 插入一行ingredient
     * */
    @Override
    public Ingredient save(Ingredient ingredient) {
        jdbc.update(
                "insert into Ingredient (id, name, type) values (?, ?, ?)",
                ingredient.getId(),
                ingredient.getName(),
                ingredient.getType().toString());
        return ingredient;
    }
}