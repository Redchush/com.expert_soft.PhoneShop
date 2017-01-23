package com.expert_soft.model;


import com.expert_soft.validator.group.G_Cart;
import com.expert_soft.validator.group.G_Phone;

import javax.validation.constraints.*;
import java.math.BigDecimal;

public class Phone {

//    groups = G_Cart.Item.class
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

    @Max(value = 200, groups = G_Phone.Save.class)
    private Integer width;

    @Max(value = 200, groups = G_Phone.Save.class)
    private Integer length;

    @NotNull(groups = G_Phone.Save.class)
    @DecimalMin(value = "0.0", groups = G_Phone.Save.class)
    private BigDecimal price;

    private Integer camera;

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

        if (key != null ? !key.equals(phone.key) : phone.key != null) {
            return false;
        }
        if (model != null ? !model.equals(phone.model) : phone.model != null) {
            return false;
        }
        if (color != null ? !color.equals(phone.color) : phone.color != null) {
            return false;
        }
        if (displaySize != null ? !displaySize.equals(phone.displaySize) : phone.displaySize != null) {
            return false;
        }
        if (width != null ? !width.equals(phone.width) : phone.width != null) {
            return false;
        }
        if (length != null ? !length.equals(phone.length) : phone.length != null) {
            return false;
        }
        return price != null ? price.equals(phone.price) : phone.price == null;

    }

    @Override
    public int hashCode() {
        int result = key != null ? key.hashCode() : 0;
        result = 31 * result + (model != null ? model.hashCode() : 0);
        result = 31 * result + (color != null ? color.hashCode() : 0);
        result = 31 * result + (displaySize != null ? displaySize.hashCode() : 0);
        result = 31 * result + (width != null ? width.hashCode() : 0);
        result = 31 * result + (length != null ? length.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        return result;
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
