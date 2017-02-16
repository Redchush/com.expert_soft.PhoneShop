package com.expert_soft.service;


import com.expert_soft.model.Phone;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;


public interface PhoneService {

    List<Phone> findAll();

    /**
     * @param keys - keys of possible phones
     * @return if keys is null or keys is empty or in database
     * there was no one phone with one of keys - return empty list.
     * Otherwise return stored in persistence list of phones.
     */
    List<Phone> getPhones(Long... keys);

    /**
     *
     * @param keys - keys of possible phones
     * @return if keys is null or keys is empty or in database
     * there was no one phone with one of keys - return empty list.
     * Otherwise return stored in persistence list of phones.
     */
    List<Phone> getPhones(Collection<Long> keys);

    /**
     *
     * @param key - phone key in persistence
     * @return null if phone not found
     */
    Phone getPhone(Long key);

    /**
     *
     * @param phone
     * @return key of phone, null if not saved
     */
    Number savePhone(@NotNull Phone phone) throws NullPointerException;

}
