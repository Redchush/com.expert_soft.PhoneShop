package com.expert_soft.model;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserInfo {

    @NotNull
    @Size(min = 3, max = 100, message = "{userInfo.firstName.size}")
    @Pattern(regexp = "[a-z.]+", message = "The first name {common.englishPattern}")
    private String firstName;

    @NotNull
    @Size(min = 3, max = 100, message = "{userInfo.lastName.size}")
    @Pattern(regexp = "[a-z.]+", message = "The last name {common.englishPattern}")
    private String lastName;

    @NotNull
    @Size(min = 10, max = 500, message = "{userInfo.deliveryAddress.size}")
    private String deliveryAddress;

    @NotNull
    @Size(min = 6, max = 20, message = "{userInfo.contactPhoneNo.size}")
    @Pattern(regexp = "[/+]?[0-9 ]{6,20}",
             message = "{userInfo.contactPhoneNo.pattern}")
    private String contactPhoneNo;

    @Size(max = 500, message = "The length of additional information must not be more than 500 digits")
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
}
