package com.expert_soft.service.impl;


import com.expert_soft.model.OrderItem;
import com.expert_soft.model.order.Cart;
import com.expert_soft.model.order.Order;
import com.expert_soft.service.CartService;
import com.expert_soft.test_util.DataBuilder;
import com.expert_soft.test_util.db.CountRowResponsible;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import javax.sql.DataSource;
import java.math.BigDecimal;

import static com.expert_soft.test_util.asserts.ModelAsserts._assertEquals;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
        "classpath:context/core_root-context.xml"
})
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
})
@ActiveProfiles("test")
public class CartServiceImplIntTest {

    private static final Logger logger = Logger.getLogger(CartServiceImplTest.class);

    @Autowired private CartService service;
    @Autowired private DataSource source;

    private CountRowResponsible rowCounter;
    private Order order;
    private Cart fullCart;

    @Before
    public void setUp() throws Exception {
        order = DataBuilder.Order_2.getOrderByCart();
        fullCart = DataBuilder.Carts.byOrder_2();
        rowCounter = new CountRowResponsible(new JdbcTemplate(source));
    }

    @Test
    public void addToCart_CalculatorMerge(){
        OrderItem first = DataBuilder.Order_2.getItem_1();
        OrderItem second = DataBuilder.Order_2.getItem_2();
        Cart expected = DataBuilder.Carts.byOrder_2();

        Cart actual = new Cart();
        service.addToCart(actual, first.getPhone(), first.getQuantity());
        service.addToCart(actual, second.getPhone(), second.getQuantity());

        assertEquals("Fail to calculate new quantity", 2, actual.getOrderItems().size());
        assertEquals("Fail to calculate new subtotal", expected.getSubtotal(), actual.getSubtotal());
    }

    @Test
    public void deleteFromCart_CalculatorMerge(){
        Cart expected = DataBuilder.Carts.byOrder_1();
        Cart actual = DataBuilder.Carts.byOrder_2();
        OrderItem second = DataBuilder.Order_2.getItem_2();
        BigDecimal prevousSubtotal =  actual.getSubtotal();

        OrderItem itemDeleted = service.deleteFromCart(actual, second.getPhone().getKey());
        BigDecimal currentSubtotal =  actual.getSubtotal();

        assertEquals("Returned incorrect item", second, itemDeleted);
        assertThat("Subtotal after deleted item not lessen",
                currentSubtotal, lessThan(prevousSubtotal));
        assertEquals("Subtotal incorrect. Try test OrderCalculator",
                expected.getSubtotal(), actual.getSubtotal());
        _assertEquals(expected, actual);

    }

    @Test
    public void deleteFromCart_Last_CalculatorMerge(){
        Cart actual = DataBuilder.Carts.byOrder_1();
        OrderItem item = DataBuilder.Order_1.getItem_1_new();

        service.deleteFromCart(actual, item.getPhone().getKey());
        assertEquals(actual.getSubtotal(), new BigDecimal("0.00"));
    }


    @Test(expected = NullPointerException.class)
    public void addToCart_NonExistentPhone() throws Exception {
        service.addToCart(new Cart(), 100L, 3);
    }
}
