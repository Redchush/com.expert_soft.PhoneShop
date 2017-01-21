package com.expert_soft.service;




import com.expert_soft.exception.service.NotUniqueEntityException;
import com.expert_soft.model.Phone;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.Collection;
import java.util.List;

public interface PhoneService {

    List<Phone> findAll();


    /**
     *
     * @param keys - keys of possible phones
     * @return if keys is null or keys is empty - return empty collection.
     * Otherwise return stored in persistence list of phones.
     */
    List<Phone> getPhones(Long... keys) throws EmptyResultDataAccessException;;

    /**
     *
     * @param keys - keys of possible phones
     * @return if keys is null or keys is empty - return empty collection.
     * Otherwise return stored in persistence list of phones.
     */
    List<Phone> getPhones(Collection<Long> keys) throws EmptyResultDataAccessException;

    Phone getPhone(Long id);

    void savePhone(Phone phone) throws NotUniqueEntityException;
}
