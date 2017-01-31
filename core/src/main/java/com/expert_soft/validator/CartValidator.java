package com.expert_soft.validator;


import com.expert_soft.model.order.Cart;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class CartValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
       return Cart.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Cart user = (Cart) target;
    }
}
