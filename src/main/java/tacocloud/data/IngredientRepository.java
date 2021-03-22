package tacocloud.data;

import org.springframework.data.repository.CrudRepository;
import tacocloud.Ingredient;

/**
 * 第一个参数是存储库要持久化的实体类型，第二个参数是实体 id 属性的类型
 * 声明 JPA repository
 * */
public interface IngredientRepository extends CrudRepository<Ingredient, String> {

    /**
     * jdbcTemplate 的 API
     */
//    Iterable<Ingredient> findAll(); // 查询所有的 Ingredient 使之变成一个 Ingredient 的集合对象

//    Ingredient findOne(String id); // 通过它的 id 查询单个 Ingredient

//    Ingredient save(Ingredient ingredient); // 保存一个 Ingredient 对象
}