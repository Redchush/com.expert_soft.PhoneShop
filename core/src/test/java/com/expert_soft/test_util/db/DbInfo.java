package com.expert_soft.test_util.db;


import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.jdbc.JdbcTestUtils;

public class DbInfo {

    public static final String ORDERS = "ORDERS";
    public static final String ORDER_ITEMS = "ORDER_ITEMS";
    public static final String PHONES = "PHONES";

    private String script = "ALTER TABLE ALTER COLUMN %s RESTART WITH %s";
    private int orders;
    private int orderItems;
    private int phones;
    private JdbcTemplate template;

    public DbInfo(JdbcTemplate template) {
        this.template = template;
        orders =  getDynamicOrders();
        orderItems =  getDynamicOrderItems();
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
        return JdbcTestUtils.countRowsInTable(template, PHONES);
    }

    public int getDynamicOrders(){
        return JdbcTestUtils.countRowsInTable(template, ORDERS);
    }

    public int getDynamicOrderItems(){
        return JdbcTestUtils.countRowsInTable(template, ORDER_ITEMS);
    }

    public String getOrdersScript(){
        return String.format(script, ORDERS + ".ID", orders + 1);
    }

    public String getItemsScript(){
        return String.format(script, ORDER_ITEMS, orderItems + 1);
    }

    public void execute(String ... script){
        for (String string : script) {
            template.execute(string);
        }
    }
}
