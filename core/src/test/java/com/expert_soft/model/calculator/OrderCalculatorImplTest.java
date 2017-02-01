package com.expert_soft.model.calculator;

import com.expert_soft.model.OrderItem;
import com.expert_soft.model.order.Cart;
import com.expert_soft.model.order.Order;
import com.expert_soft.test_util.DataBuilder;
import org.junit.Test;


import static com.expert_soft.test_util.asserts.ModelAsserts._assertEquals;
import static org.junit.Assert.*;

public class OrderCalculatorImplTest {

    private OrderCalculator calculator = new OrderCalculatorImpl();

    @Test
    public void recalculateCart_FullCheck() throws Exception {
        Cart expected = DataBuilder.Carts.byOrder_1();
        Cart actual = DataBuilder.Carts.byOrder_1_clean();
        calculator.recalculate(actual);
        _assertEquals(expected, actual);


        Cart expected_2 = DataBuilder.Carts.byOrder_1();
        Cart actual_2 = DataBuilder.Carts.byOrder_1_clean();
        calculator.recalculate(actual_2);
        _assertEquals(expected_2, actual_2);
    }

    @Test
    public void recalculateOrder_FullCheck() throws Exception{
        fullCheck(DataBuilder.Order_1.getOrder(), DataBuilder.Order_1.getOrder());
        fullCheck(DataBuilder.Order_2.getOrder(), DataBuilder.Order_2.getOrder());
    }

    private void fullCheck(Order expected, Order actual){
        actual.setTotalPrice(null);
        actual.setSubtotal(null);
        calculator.recalculate(actual);
        assertEquals("Incorrect total price in order ", expected.getTotalPrice(), actual.getTotalPrice());
        assertEquals("Incorrect subtotal in order ", expected.getSubtotal(), actual.getSubtotal());
    }

    @Test
    public void recalculateCart_SizeCheck() throws Exception {
        Integer expectedSize = 10;
        Cart cart = new Cart();

        cart.addItem(new OrderItem(DataBuilder.getRandomPhone(), 2));
        cart.addItem(new OrderItem(DataBuilder.getRandomPhone(), 4));
        cart.addItem(new OrderItem(DataBuilder.getRandomPhone(), 4));
        calculator.recalculate(cart);
        assertEquals("cart size invalid calculation. Cart tested: " + cart,
                expectedSize, cart.getTotalPhonesCount());
    }
}