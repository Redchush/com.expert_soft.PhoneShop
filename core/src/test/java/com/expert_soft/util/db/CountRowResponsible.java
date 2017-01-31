package com.expert_soft.util.db;


import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.jdbc.JdbcTestUtils;

public class CountRowResponsible {

    private int orders;
    private int orderItems;
    private int phones;
    private JdbcTemplate template;

    public CountRowResponsible(JdbcTemplate template) {
        this.template = template;
        orders =  JdbcTestUtils.countRowsInTable(template, "ORDERS");
        orderItems =  JdbcTestUtils.countRowsInTable(template, "ORDER_ITEMS");
        phones = getDynamicPhones();
    }

    public int getOrders() {
        return orders;
    }

    public int getOrderItems() {
        return orderItems;
    }

    public int getPhones() {return phones;}

    public int getDynamicPhones() {
        return JdbcTestUtils.countRowsInTable(template, "PHONES");
    }
}
