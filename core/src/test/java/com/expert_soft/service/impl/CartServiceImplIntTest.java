package com.expert_soft.service.impl;


import com.expert_soft.model.order.Cart;
import com.expert_soft.model.order.OrderItem;
import com.expert_soft.test_util.Context;
import com.expert_soft.test_util.DataBuilder;
import com.expert_soft.test_util.db.DbInfo;
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
@ContextConfiguration(locations={Context.ROOT_WITH_CART})
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
})
@ActiveProfiles("test")
public class CartServiceImplIntTest {

    private static final Logger logger = Logger.getLogger(CartServiceImplTest.class);

    @Autowired private CartServiceImpl service;
    @Autowired private DataSource source;
    private DbInfo rowCounter;
    private Cart actual;

    @Before
    public void setUp() throws Exception {
        rowCounter = new DbInfo(new JdbcTemplate(source));
        service.setCart(new Cart());
        actual = service.getCart();
    }

   @Test
    public void addToCart_CalculatorMerge(){
        OrderItem first = DataBuilder.Order_2.getItem_1();
        OrderItem second = DataBuilder.Order_2.getItem_2();
        Cart expected = DataBuilder.Carts.byOrder_2();


        service.addToCart(first.getPhone(), first.getQuantity());
        service.addToCart(second.getPhone(), second.getQuantity());

        assertEquals("Fail to calculate new quantity", 2, actual.getOrderItems().size());
        assertEquals("Fail to calculate new subtotal", expected.getSubtotal(), actual.getSubtotal());
    }

    @Test
    public void deleteFromCart_CalculatorMerge(){
        Cart expected = DataBuilder.Carts.byOrder_1();

        Cart actual = DataBuilder.Carts.byOrder_2();
        service.setCart(actual);

        OrderItem second = DataBuilder.Order_2.getItem_2();
        BigDecimal prevSubtotal =  actual.getSubtotal();

        OrderItem itemDeleted = service.deleteFromCart(second.getPhone().getKey());
        BigDecimal currentSubtotal =  actual.getSubtotal();

        assertEquals("Returned incorrect item", second, itemDeleted);
        assertThat("Subtotal after deleted item not lessen",
                currentSubtotal, lessThan(prevSubtotal));
        assertEquals("Subtotal incorrect. Try test OrderCalculator",
                expected.getSubtotal(), actual.getSubtotal());
        _assertEquals(expected, actual);

    }

    @Test
    public void deleteFromCart_Last_CalculatorMerge(){
        Cart actual = DataBuilder.Carts.byOrder_1();
        service.setCart(actual);

        OrderItem item = DataBuilder.Order_1.getItem_1_new();

        service.deleteFromCart(item.getPhone().getKey());
        assertEquals(actual.getSubtotal(), new BigDecimal("0.00"));
    }


    @Test(expected = NullPointerException.class)
    public void addToCart_NonExistentPhone() throws Exception {
        service.addToCart(100L, 3);
    }
}
