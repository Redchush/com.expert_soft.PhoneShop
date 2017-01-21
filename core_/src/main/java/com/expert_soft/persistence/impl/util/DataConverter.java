package com.expert_soft.persistence.impl.util;


import com.expert_soft.model.Phone;

import java.math.BigDecimal;

public class DataConverter {

    public static BigDecimal getPhonePriceForPersistence(Phone phone){
        return phone.getPrice().multiply(new BigDecimal(100));
    }

    public static BigDecimal getPhonePriceForModel(BigDecimal decimal){
        return decimal.divide(new BigDecimal(100), 2, BigDecimal.ROUND_CEILING);
    }
}
