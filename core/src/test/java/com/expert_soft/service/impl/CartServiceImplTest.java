package com.expert_soft.service.impl;

import com.expert_soft.model.Cart;
import com.expert_soft.model.Order;
import com.expert_soft.model.OrderItem;
import com.expert_soft.model.Phone;
import com.expert_soft.persistence.PhoneDao;
import com.expert_soft.service.CartService;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.math.BigDecimal;
import java.util.Properties;

import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
//        "classpath:persistence-context.xml",
//        "classpath:test-dataSource-context.xml",
        "classpath:core_test-bean.xml",
//        "classpath:service-context.xml",
        "classpath:mockService.xml"
})
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
})
public class CartServiceImplTest {


    private static final Logger logger = Logger.getLogger(CartServiceImplTest.class);

    @Autowired
    @Qualifier("cartService")
    private CartService service;

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private PhoneDao dao;

    @Autowired private ApplicationContext ac;

    @Autowired @Qualifier("orderItem_secondOrder_1_new")
    private OrderItem firstItem;
    @Autowired @Qualifier("orderItem_secondOrder_2_new")
    private OrderItem secondItem;

    @Autowired @Qualifier("valid_msg") Properties properties;

    private Cart emptyCart;
    private Order order;
    private Cart fullCart_2_items;
    private Cart fullCart_1_item;

    @Before
    public void setUp() throws Exception {
        emptyCart = new Cart();
        order = (Order) ac.getBean("order_2_new_calculated");
        fullCart_2_items = ac.getBean("cart_2_calculated", Cart.class);
        fullCart_1_item = ac.getBean("cart_1_calculated", Cart.class);
        MockitoAnnotations.initMocks(this);

    }

//    @Test(expected = ConstraintViolationException.class)
    public void deepAddToCart(){
        service.setPhoneDao(dao);
        service.deepAddToCart(emptyCart, 1L, -1);

    }

    @Test
    public void addToCart1() throws Exception {
        Cart cart = new Cart();
        cart.putItem(firstItem.getPhone().getKey(), firstItem);
        service.addToCart(emptyCart, firstItem.getPhone(), firstItem.getQuantity());

        assertEquals(emptyCart, cart);

        OrderItem item = new OrderItem(firstItem.getPhone(), 1);
        service.addToCart(emptyCart, item.getPhone(), item.getQuantity());

        assertEquals(emptyCart.getItemsMap().size(), 1);
        OrderItem item1 = emptyCart.getItem(firstItem.getPhone().getKey());
        assertEquals("Fail to calculate new quantity",
                item1.getQuantity(),
                new Integer(2));
    }

    @Test
    public void addToCart_TEN(){
        Phone phone = new Phone();
        phone.setKey(1L);
        service.addToCart(emptyCart, phone, 4);
        service.addToCart(emptyCart, phone, 6);
        assertEquals(10, emptyCart.getItem(1L).getQuantity().intValue());

    }

    @Test(expected = ConstraintViolationException.class)
    public void addToCart_To_Much() throws Exception {
        Phone phone = new Phone();
        phone.setKey(1L);
        service.addToCart(emptyCart, phone, 12);
    }

    @Test
    public void deleteFromCart() throws Exception {
        service.deleteFromCart(fullCart_2_items, 1L);
        assertEquals(1, fullCart_2_items.getItemsMap().size());
    }

    @Test
    public void deleteFromCart1() throws Exception {
        service.deleteFromCart(fullCart_2_items, new Long[]{1L, 2L});
        assertTrue(fullCart_2_items.getItemsMap().isEmpty());
    }

    @Test
    public void changeQuantity() throws Exception {
        Integer expPhoneQuantity=4;
        int sizeInitial = fullCart_2_items.getItemsMap().size();


        OrderItem item = new OrderItem(new Phone(1L), expPhoneQuantity);

        service.updatePhoneQuantity(fullCart_2_items, item);
        assertEquals("Quantity of order items in cart changed",
                sizeInitial, fullCart_2_items.getItemsMap().size());

        Integer realQuantity = fullCart_2_items.getItem(1L)
                                               .getQuantity();

        assertEquals(expPhoneQuantity, realQuantity);
    }

    @Test
    public void calculateAndSetSubtotal() throws Exception {
        BigDecimal expected = fullCart_1_item.getSubtotal();
        BigDecimal actual = service.calculateAndSetSubtotal(fullCart_1_item);

        assertEquals(expected, actual);
        Cart cart = new Cart();
        cart.putItem(firstItem.getPhone().getKey(), firstItem);

        BigDecimal actual_2 = service.calculateAndSetSubtotal(cart);
        assertEquals(firstItem.getPhone().getPrice(), actual_2);
    }

    @Test
    public void calculateAndSetSize() throws Exception {
        Cart expected = ac.getBean("cart_2_calculated", Cart.class);
        Cart actual = service.calculateAndSetSize(fullCart_2_items);
        assertEquals("cart size invalid calculation. Cart tested: " +
                fullCart_2_items, expected.getCartSize(), actual.getCartSize());
    }

    @Test(expected = ConstraintViolationException.class)
    public void createOrderItemValidation() throws Exception {
        Phone phone = firstItem.getPhone();
        service.createNewOrderItem(phone, 12);
    }

    @Test
    public void createOrderItemValidationMsg() throws Exception {
        try {
            Phone phone = firstItem.getPhone();
            service.createNewOrderItem(phone, 12);
            assertFalse("validation don't work", true);
        } catch (ConstraintViolationException e){
            String expectedMsg = properties.getProperty("orderItem.quantity.max");
            ConstraintViolation<?> next = e.getConstraintViolations().iterator().next();
            assertEquals(12, next.getInvalidValue());
            assertEquals(expectedMsg, next.getMessage());
        }
    }


}