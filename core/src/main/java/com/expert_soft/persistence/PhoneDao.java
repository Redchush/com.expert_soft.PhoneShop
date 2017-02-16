package com.expert_soft.persistence;




import com.expert_soft.model.Phone;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.Collection;
import java.util.List;

public interface PhoneDao {

    /**
     *
     * @param key - phone key
     * @throws EmptyResultDataAccessException - if phone with this key not found
     */
    Phone getPhone(Long key) throws EmptyResultDataAccessException;

    /**
     *
     * @param keys - keys of possible phones
     * @return if keys is null or keys is empty - return empty collection.
     * Otherwise return stored in persistence list of phones.
     */
    List<Phone> getPhones(Long... keys) throws EmptyResultDataAccessException;

    /**
     *
     * @param keys - keys of possible phones
     * @return if keys is null or keys is empty - return empty collection.
     * Otherwise return stored in persistence list of phones.
     */
    List<Phone> getPhones(Collection<Long> keys) throws EmptyResultDataAccessException;

    /**
     *
     * @param phone - phone to be saved in persistence
     * @return null if phone not saved because of not unique form
     */
    Number savePhone(Phone phone) throws DuplicateKeyException;

    List<Phone> findAll();

}
