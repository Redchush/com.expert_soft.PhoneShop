package com.expert_soft.util;


import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.jdbc.JdbcTestUtils;

public class CountRowResponsible {

    private JdbcTemplate template;

    private int orders;
    private int orderItems;
    private int phones;

    public CountRowResponsible(JdbcTemplate template) {
        this.template = template;
        orders =  JdbcTestUtils.countRowsInTable(template, "ORDERS");
        orderItems =  JdbcTestUtils.countRowsInTable(template, "ORDER_ITEMS");
        phones =  JdbcTestUtils.countRowsInTable(template, "PHONES");
    }

    public int getOrders() {
        return orders;
    }

    public int getOrderItems() {
        return orderItems;
    }

    public int getPhones() {
        return phones;
    }
}
