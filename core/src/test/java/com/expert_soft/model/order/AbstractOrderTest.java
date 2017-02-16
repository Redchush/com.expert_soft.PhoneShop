package com.expert_soft.model.order;


import com.expert_soft.test_util.DataBuilder;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;

import static org.junit.Assert.assertEquals;

public class AbstractOrderTest {

    private Cart cart;
    private OrderItem item;

    @Before
    public void setUp() throws Exception {
        cart = new Cart();
        item = new OrderItem(DataBuilder.getRandomPhone(), 1);
        cart.addItem(item);
    }

    @Test
    public void getItems_valid(){
        assertEquals(cart.getOrderItems().size(), 1);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void getItems(){
        Collection<OrderItem> orderItems = cart.getOrderItems();
        orderItems.remove(item);
    }


}
