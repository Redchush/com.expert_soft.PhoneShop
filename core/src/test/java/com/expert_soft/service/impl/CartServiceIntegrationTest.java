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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Properties;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
        "classpath:persistence-context.xml",
        "classpath:test-dataSource-context.xml",
        "classpath:core_test-bean.xml",
        "classpath:service-context.xml"
})
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
})
public class CartServiceIntegrationTest {

    private static final Logger logger = Logger.getLogger(CartServiceImplTest.class);

    @Autowired private CartService service;
    @Autowired private ApplicationContext ac;

    @Autowired @Qualifier("orderItem_secondOrder_1_new")
    private OrderItem firstItem;
    @Autowired @Qualifier("orderItem_secondOrder_2_new")
    private OrderItem secondItem;

    @Autowired @Qualifier("valid_msg")
    Properties properties;

    private Cart emptyCart;
    private Order order;
    private Cart fullCart;

    @Before
    public void setUp() throws Exception {
        HibernateValidatorConfiguration configuration = Validation.byProvider(HibernateValidator.class).configure();

        configuration.allowMultipleCascadedValidationOnReturnValues(true)
                     .allowOverridingMethodAlterParameterConstraint(true)
                     .allowParallelMethodsDefineParameterConstraints(true);

        emptyCart = new Cart();
        order = (Order) ac.getBean("order_2_new_calculated");
        HashSet<OrderItem> orderItems = new HashSet<OrderItem>(Arrays.asList(firstItem, secondItem));
        order.setOrderItems(orderItems);
        fullCart = DataBuilder.buildCartWithoutSubtotal(order);
    }

    @Test
    public void addToCart() throws Exception {
        Cart cart = new Cart();
        cart.putItem(firstItem.getPhone().getKey(), firstItem);

        service.deepAddToCart(emptyCart, firstItem.getPhone().getKey(), firstItem.getQuantity());
        assertEquals(cart, emptyCart);

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
