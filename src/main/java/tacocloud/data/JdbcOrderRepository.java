import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import tacocloud.Order;
import tacocloud.Taco;
import tacocloud.data.OrderRepository;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcOrderRepository implements OrderRepository {
    private SimpleJdbcInsert orderInserter;
    private SimpleJdbcInsert orderTacoInserter;
    private ObjectMapper objectMapper;

    /**
     * JdbcOrderRepository 也通过其构造函数注入了 JdbcTemplate。
     * 但是，构造函数并没有将 JdbcTemplate 直接分配给一个实例变量，
     * 而是使用它来构造两个 SimpleJdbcInsert 实例
     * */
    @Autowired
    public JdbcOrderRepository(JdbcTemplate jdbc) {
        this.orderInserter = new SimpleJdbcInsert(jdbc)
                .withTableName("Taco_Order")
                .usingGeneratedKeyColumns("id");
        this.orderTacoInserter = new SimpleJdbcInsert(jdbc)
                .withTableName("Taco_Order_Tacos");
        this.objectMapper = new ObjectMapper();
    }

    /**
     * 定义了保存订单及其关联 Taco 对象的流
     * 并将持久性工作委托给 saveOrderDetails() 和 saveTacoToOrder()
     * */
    @Override
    public Order save(Order order) {
        order.setPlacedAt(new Date());
        long orderId = saveOrderDetails(order);
        order.setId(orderId);
        List<Taco> tacos = order.getTacos();
        for (Taco taco : tacos) {
            saveTacoToOrder(taco, orderId);
        }
        return order
    }

    /**
     * 不再使用繁琐的 PreparedStatementCreator, 而是使用SimpleJdbcInsert
     * */
    private long saveOrderDetails(Order order) {
        @SuppressWarnings("unchecked")
        Map<String, Object> values = objectMapper.convertValue(order, Map.class);
    }

    private void saveTacoToOrder(Taco taco, long orderId) {

    }
}