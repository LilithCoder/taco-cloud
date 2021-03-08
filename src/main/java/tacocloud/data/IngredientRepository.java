package tacocloud.data;

import tacocloud.Ingredient;

public interface IngredientRepository {

    Iterable<Ingredient> findAll(); // 查询所有的 Ingredient 使之变成一个 Ingredient 的集合对象

    Ingredient findOne(String id); // 通过它的 id 查询单个 Ingredient

    Ingredient save(Ingredient ingredient); // 保存一个 Ingredient 对象
}