package com.expert_soft.form;


import com.expert_soft.model.Phone;
import com.expert_soft.model.order.OrderItem;
import com.expert_soft.validator.group.G_Cart;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.Arrays;


@Validated(G_Cart.Item.class)
public class UpdateCartForm {

    private @Valid OrderItem[] items;

    public UpdateCartForm() {
        items = new OrderItem[0];
    }

    public UpdateCartForm(OrderItem[] items) {
        this.items = items;
    }

    public UpdateCartForm(int size) {
        items = new OrderItem[size];
        for (int i = 0; i < size; i++) {
            items[i] = new OrderItem();
            items[i].setPhone(new Phone());
        }
    }

    public OrderItem[] getItems() {
        return items;
    }

    public void setItems(OrderItem[] items) {
        this.items = items;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UpdateCartForm{");
        sb.append("items=").append(Arrays.toString(items));
        sb.append('}');
        return sb.toString();
    }
}
