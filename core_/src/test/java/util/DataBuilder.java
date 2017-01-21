package util;


import com.expert_soft.model.Cart;
import com.expert_soft.model.Order;
import com.expert_soft.model.OrderItem;

import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

public class DataBuilder {

    public static Cart buildCartWithoutSubtotal(Order order){
        Cart cart = new Cart();
        ConcurrentMap<Long, OrderItem> collect =
                         order.getOrderItems().stream().collect(Collectors
                        .toConcurrentMap(s -> s.getPhone()
                                               .getKey(),
                                s -> s));
        cart.setItemsMap(collect);
        return cart;
    }
}
