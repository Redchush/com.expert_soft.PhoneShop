package com.expert_soft.service.impl;

import com.expert_soft.model.Cart;
import com.expert_soft.model.Order;
import com.expert_soft.model.OrderItem;
import com.expert_soft.model.Phone;
import com.expert_soft.service.CartService;
import com.expert_soft.util.DataBuilder;
import org.apache.log4j.Logger;
import org.hibernate.validator.HibernateValidator;
import org.hibernate.validator.HibernateValidatorConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import javax.validation.ConstraintViolationException;
import javax.validation.Validation;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
        "classpath:context/core_root-context.xml"
})
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
})
@ActiveProfiles("dev")
public class CartServiceImplIntTest {

    private static final Logger logger = Logger.getLogger(CartServiceImplTest.class);

    @Autowired private CartService service;
    private OrderItem firstItem;

    private Cart emptyCart;
    private Order order;
    private Cart fullCart;

    @Before
    public void setUp() throws Exception {
        emptyCart = new Cart();
        order = DataBuilder.Order_2.getOrderCalculated();
        fullCart = DataBuilder.Carts.byOrder_2();
        firstItem = DataBuilder.Order_2.getItem_1();
    }

    @Test
    public void addToCart() throws Exception {
        Cart cart = new Cart();
        cart.putItem(firstItem.getPhone().getKey(), firstItem);

        service.deepAddToCart(emptyCart, firstItem.getPhone().getKey(), firstItem.getQuantity());
        assertEquals(cart.getItemsMap(), emptyCart.getItemsMap());

        OrderItem item = new OrderItem();
        item.setPhone(firstItem.getPhone());
        service.deepAddToCart(cart, firstItem.getPhone().getKey(), 1);
        assertEquals(cart.getItemsMap().size(), 1);
        assertEquals("Fail to calculate new quantity",
                cart.getItemsMap().get(firstItem.getPhone().getKey()).getQuantity(),
                new Integer(2));
    }

    @Test(expected = ConstraintViolationException.class)
    public void addToCart_To_Much() throws Exception {
        Phone phone = new Phone();
        phone.setKey(1L);
        service.addToCart(emptyCart, phone, 12);
    }

    @Test(expected = ConstraintViolationException.class)
    public void addToCart_To_Much_SUM() throws Exception {
        Phone phone = new Phone();
        phone.setKey(1L);
        emptyCart.putItem(new OrderItem(phone, 9));
        service.addToCart(emptyCart, phone, 3);
    }

    @Test(expected = ConstraintViolationException.class)
    public void deepAddToCart(){
        service.deepAddToCart(emptyCart, 1L, 20);
    }

    @Test(expected = ConstraintViolationException.class)
    public void createOrderItemValidation() throws Exception {
        Phone phone = firstItem.getPhone();
        service.createNewOrderItem(phone, 12);
    }

}
