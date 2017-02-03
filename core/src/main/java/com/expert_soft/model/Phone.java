package com.expert_soft.model;


import com.expert_soft.validator.group.G_Cart;
import com.expert_soft.validator.group.G_Phone;

import javax.validation.constraints.*;
import java.math.BigDecimal;

@SuppressWarnings("ALL")
public class Phone {

    @NotNull(message = "{common.key}", groups = G_Cart.Item.class)
    @Min(value = 1, message = "{common.key}", groups = G_Cart.Item.class)
    private Long key;

    @NotNull(groups = G_Phone.Save.class)
    @Size(max = 254, groups = G_Phone.Save.class)
    private String model;

    @NotNull(groups = G_Phone.Save.class)
    private String color;

    @NotNull(groups = G_Phone.Save.class)
    @Max(value = 200, groups = G_Phone.Save.class)
    private Integer displaySize;

    @NotNull(groups = G_Phone.Save.class)
    @DecimalMin(value = "0.0", groups = G_Phone.Save.class)
    private BigDecimal price;

    @Max(value = 200, groups = G_Phone.Save.class)
    private Integer width;

    @Max(value = 200, groups = G_Phone.Save.class)
    private Integer length;

    private Integer camera;

    public Phone() {}

    public Phone(Long key) {
        this.key = key;
    }

    public Phone(Long key, String model, String color,
                 Integer displaySize, BigDecimal price){
        this.key = key;
        this.model = model;
        this.color =color;
        this.displaySize = displaySize;
        this.price = price;
    }


    public Long getKey() {
        return key;
    }

    public void setKey(Long key) {
        this.key = key;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getDisplaySize() {
        return displaySize;
    }

    public void setDisplaySize(Integer displaySize) {
        this.displaySize = displaySize;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Integer getCamera() {
        return camera;
    }

    public void setCamera(Integer camera) {
        this.camera = camera;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Phone phone = (Phone) o;
        return key != null ? key.equals(phone.key) : phone.key == null;
    }

    @Override
    public int hashCode() {
        return key != null ? key.hashCode() : 0;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Phone{");
        sb.append("key=").append(key);
        sb.append(", model='").append(model).append('\'');
        sb.append(", color='").append(color).append('\'');
        sb.append(", displaySize=").append(displaySize);
        sb.append(", width=").append(width);
        sb.append(", length=").append(length);
        sb.append(", price=").append(price);
        sb.append('}');
        return sb.toString();
    }


}
