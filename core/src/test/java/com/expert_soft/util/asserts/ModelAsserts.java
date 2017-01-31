package com.expert_soft.util.asserts;


import com.expert_soft.model.Cart;
import com.expert_soft.model.Order;
import com.expert_soft.model.Phone;

import static junit.framework.TestCase.assertTrue;

public class ModelAsserts {

    public static void _assertEquals(Cart o1, Cart o2){
        boolean result = Comparators.equals(o1, o2);
        assertTrue(Comparators.failMsg(o1, o2), result);
    }

    public static void _assertEquals(String message, Cart o1, Cart o2){
        boolean result = Comparators.equals(o1, o2);
        assertTrue(message + "\n" + Comparators.failMsg(o1, o2), result);
    }

    public static void _assertEquals(Order o1, Order o2){
        boolean result = Comparators.equals(o1, o2);
        assertTrue(Comparators.failMsg(o1, o2), result);
    }

    public static void _assertEquals(String message, Order o1, Order o2){
        boolean result = Comparators.equals(o1, o2);
        assertTrue(message + "\n" + Comparators.failMsg(o1, o2), result);
    }

    public static void _assertEquals(Phone o1, Phone o2){
        boolean result = Comparators.equals(o1, o2);
        assertTrue(Comparators.failMsg(o1, o2), result);
    }

    public static void _assertEquals(String message, Phone o1, Phone o2){
        boolean result = Comparators.equals(o1, o2);
        assertTrue(message + "\n" + Comparators.failMsg(o1, o2), result);
    }

}
