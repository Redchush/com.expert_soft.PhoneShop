package com.expert_soft.model.order;


import com.expert_soft.validator.group.G_Order;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@SuppressWarnings("ALL")
public class UserInfo {

    @NotNull(groups = G_Order.Info.class)
    @Size(min = 3, max = 100, message = "{userInfo.firstName.size}",
            groups = G_Order.Info.class)
    @Pattern(regexp = "[a-z.]+", message = "The first name {common.englishPattern}",
            groups = G_Order.Info.class)
    private String firstName;

    @NotNull(groups = G_Order.Info.class)
    @Size(min = 3, max = 100, message = "{userInfo.lastName.size}",   groups = G_Order.Info.class)
    @Pattern(regexp = "[a-z.]+", message = "The last name {common.englishPattern}",
            groups = G_Order.Info.class)
    private String lastName;

    @NotNull(groups = G_Order.Info.class)
    @Size(min = 10, max = 500, message = "{userInfo.deliveryAddress.size}", groups = G_Order.Info.class)
    private String deliveryAddress;

    @NotNull(groups = G_Order.Info.class)
    @Size(min = 6, max = 20, message = "{userInfo.contactPhoneNo.size}",   groups = G_Order.Info.class)
    @Pattern(regexp = "[/+]?[0-9 ]{6,20}",
             message = "{userInfo.contactPhoneNo.pattern}",   groups = G_Order.Info.class)
    private String contactPhoneNo;

    @Size(max = 500,
            message = "The length of additional information must not be more than 500 digits",
            groups = G_Order.Info.class)
    private String additionalInfo;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getContactPhoneNo() {
        return contactPhoneNo;
    }

    public void setContactPhoneNo(String contactPhoneNo) {
        this.contactPhoneNo = contactPhoneNo;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    @SuppressWarnings("SimplifiableIfStatement")
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UserInfo userInfo = (UserInfo) o;

        if (firstName != null ? !firstName.equals(userInfo.firstName) : userInfo.firstName != null) {
            return false;
        }
        if (lastName != null ? !lastName.equals(userInfo.lastName) : userInfo.lastName != null) {
            return false;
        }
        if (deliveryAddress != null ? !deliveryAddress.equals(userInfo.deliveryAddress)
                                    : userInfo.deliveryAddress != null) {
            return false;
        }
        if (contactPhoneNo != null ? !contactPhoneNo.equals(userInfo.contactPhoneNo)
                                   : userInfo.contactPhoneNo != null) {
            return false;
        }
        return additionalInfo != null ? additionalInfo.equals(userInfo.additionalInfo)
                                      : userInfo.additionalInfo == null;

    }

    @Override
    public int hashCode() {
        int result = firstName != null ? firstName.hashCode() : 0;
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (deliveryAddress != null ? deliveryAddress.hashCode() : 0);
        result = 31 * result + (contactPhoneNo != null ? contactPhoneNo.hashCode() : 0);
        result = 31 * result + (additionalInfo != null ? additionalInfo.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UserInfo{");
        sb.append("firstName='").append(firstName).append('\'');
        sb.append(", lastName='").append(lastName).append('\'');
        sb.append(", deliveryAddress='").append(deliveryAddress).append('\'');
        sb.append(", contactPhoneNo='").append(contactPhoneNo).append('\'');
        sb.append(", additionalInfo='").append(additionalInfo).append('\'');
        sb.append('}');
        return sb.toString();
    }


}
