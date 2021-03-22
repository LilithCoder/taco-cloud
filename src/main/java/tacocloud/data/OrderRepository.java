package tacocloud.data;

import org.springframework.data.repository.CrudRepository;
import tacocloud.Order;

/**
 * 第一个参数是存储库要持久化的实体类型，第二个参数是实体 id 属性的类型
 * 声明 JPA repository
 * */
public interface OrderRepository extends CrudRepository<Order, Long>{
    /**
     * jdbcTemplate 的 API
     */
//    Order save(Order order);
}
