package tacocloud.data;

//import org.springframework.data.repository.CrudRepository;
import tacocloud.Order;

public interface OrderRepository {
    Order save(Order order);
}

// 声明 JPA repository
//public interface OrderRepository extends CrudRepository<Order, Long> {
//}