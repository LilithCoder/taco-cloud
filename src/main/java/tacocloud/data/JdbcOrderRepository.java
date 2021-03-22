//package tacocloud.data;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
//import org.springframework.stereotype.Repository;
//import tacocloud.Order;
//import tacocloud.Taco;
//
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@Repository
//public class JdbcOrderRepository implements OrderRepository {
//    private SimpleJdbcInsert orderInserter;
//    private SimpleJdbcInsert orderTacoInserter;
//    private ObjectMapper objectMapper;
//
//    /**
//     * JdbcOrderRepository 也通过其构造函数注入了 JdbcTemplate。
//     * 但是，构造函数并没有将 JdbcTemplate 直接分配给一个实例变量，
//     * 而是使用它来构造两个 SimpleJdbcInsert 实例
//     * */
//    @Autowired
//    public JdbcOrderRepository(JdbcTemplate jdbc) {
//        this.orderInserter = new SimpleJdbcInsert(jdbc)
//                .withTableName("Taco_Order")
//                .usingGeneratedKeyColumns("id");
//        this.orderTacoInserter = new SimpleJdbcInsert(jdbc)
//                .withTableName("Taco_Order_Tacos");
//        this.objectMapper = new ObjectMapper();
//    }
//
//    /**
//     * 定义了保存订单及其关联 Taco 对象的流
//     * 并将持久化工作委托给 saveOrderDetails() 和 saveTacoToOrder()
//     * */
//    @Override
//    public Order save(Order order) {
//        order.setPlacedAt(new Date());
//        long orderId = saveOrderDetails(order);
//        order.setId(orderId);
//        List<Taco> tacos = order.getTacos();
//        for (Taco taco : tacos) {
//            saveTacoToOrder(taco, orderId);
//        }
//        return order;
//    }
//
//    /**
//     * 不再使用繁琐的 PreparedStatementCreator, 而是使用SimpleJdbcInsert
//     * */
//    private long saveOrderDetails(Order order) {
//        @SuppressWarnings("unchecked")
//        /**
//         * 使用 Jackson 的 ObjectMapper 及其 convertValue() 方法将 Order 转换为 Map
//         * Map 键对应于数据插入的表中的列名，映射的值被插入到这些列中
//         */
//        Map<String, Object> values = objectMapper.convertValue(order, Map.class);
//        /**
//         * 一旦 map 建立了，设置 placedAt entry 为 Order 对象的 placedAt 属性，因为 ObjectMapper 会将 Date 属性转换为 long
//         * */
//        values.put("placedAt", order.getPlacedAt());
//        /**
//         * 将订单信息保存到 Taco_Order 表中，并将数据库生成的 id 作为一个 Number 对象返回
//         * 调用 longValue() 方法将其转换为从方法返回的 long 值
//         * */
//        long orderId = orderInserter.executeAndReturnKey(values).longValue();
//        return orderId;
//
//    }
//
//    private void saveTacoToOrder(Taco taco, long orderId) {
//        /**
//         * 创建 Map 并设置适当的值，映射键对应于表中的列名
//         * */
//        Map<String, Object> values = new HashMap<>();
//        values.put("tacoOrder", orderId);
//        values.put("taco", taco.getId());
//        orderTacoInserter.execute(values);
//    }
//}