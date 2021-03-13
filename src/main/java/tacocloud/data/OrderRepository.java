package tacocloud.data;

import tacocloud.Order;

public interface OrderRepository {
    Order save(Order order);
}