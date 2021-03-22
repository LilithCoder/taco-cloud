//package tacocloud.data;
//
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.core.PreparedStatementCreator;
//import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
//import org.springframework.jdbc.support.GeneratedKeyHolder;
//import org.springframework.jdbc.support.KeyHolder;
//import org.springframework.stereotype.Repository;
//import tacocloud.Ingredient;
//import tacocloud.Taco;
//
//import java.sql.Timestamp;
//import java.sql.Types;
//import java.util.Arrays;
//import java.util.Date;
//
//@Repository
//public class JdbcTacoRepository implements TacoRepository {
//    private JdbcTemplate jdbc;
//    public JdbcTacoRepository(JdbcTemplate jdbc) {
//        this.jdbc = jdbc;
//    }
//    @Override
//    public Taco save(Taco taco) {
//        /**
//         * 将新的 taco 插入 Taco 表
//         * 获得插入 Taco 表新一行 taco 数据的id
//         * */
//        long tacoId = saveTacoInfo(taco);
//        taco.setId(tacoId);
//        /**
//         * 保存该taco每个ingredient 到 Taco_Ingredients
//         * */
//        for (Ingredient ingredient: taco.getIngredients()) {
//            saveIngredientToTaco(ingredient, tacoId);
//        }
//        return taco;
//    }
//    /**
//     * 该函数返回Taco id
//     * 在 Taco 中插入一行时，需要知道数据库生成的 id，以便在每个 Ingredient 中引用它
//     * 该函数的作用：
//     * 1. 获得插入 Taco 表新一行 taco 数据的id
//     * 2. 将该行数据用新的 update 方法插入
//     * */
//    private long saveTacoInfo(Taco taco) {
//        taco.setCreatedAt(new Date());
//        /**
//         * 创建 PreparedStatementCreator:
//         * 1. 首先创建一个 PreparedStatementCreatorFactory
//         * 2. 为它提供想要执行的 SQL，以及每个查询参数的类型
//         * 3. 然后在该工厂上调用 newPreparedStatementCreator()，在查询参数中传递所需的值以生成 PreparedStatementCreator
//         * */
//        PreparedStatementCreatorFactory preparedStatementCreatorFactory = new PreparedStatementCreatorFactory(
//                "insert into Taco (name, createdAt) values (?, ?)",
//                Types.VARCHAR, Types.TIMESTAMP
//        );
//        preparedStatementCreatorFactory.setReturnGeneratedKeys(true);
//
//        PreparedStatementCreator psc =
//                preparedStatementCreatorFactory.newPreparedStatementCreator(
//                        Arrays.asList(
//                                taco.getName(),
//                                new Timestamp(taco.getCreatedAt().getTime())));
//        KeyHolder keyHolder = new GeneratedKeyHolder();
//
//        /**
//         * 这里是一个不同的 update() 方法
//         * 保存 Ingredient 数据时使用的 update() 方法不能获得生成的 id
//         * 接受 PreparedStatementCreator 和 KeyHolder
//         * KeyHolder 将提供生成的 Taco id，但是为了使用它，还必须创建一个 PreparedStatementCreator
//         * 通过使用 PreparedStatementCreator，可以调用 update()，传入 PreparedStatementCreator 和 KeyHolder
//         * */
//        jdbc.update(psc, keyHolder);
//        /**
//         * update() 完成后，可以通过返回 keyHolder.getKey().longValue() 来返回 Taco id
//         * */
//        return keyHolder.getKey().longValue();
//    }
//    /**
//     * 使用更简单的 update() 形式来保存对 Taco_Ingredient 表引用
//     * */
//    private void saveIngredientToTaco(Ingredient ingredient, long tacoId) {
//        jdbc.update(
//                "insert into Taco_Ingredients (taco, ingredient) values (?, ?)",
//                tacoId, ingredient.getId());
//    }
//}