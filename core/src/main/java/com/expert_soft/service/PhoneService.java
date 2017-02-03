package com.expert_soft.service;




import com.expert_soft.exception.service.NotUniqueEntityException;
import com.expert_soft.model.Phone;
import com.expert_soft.validator.group.G_Phone;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;
import java.util.Collection;
import java.util.List;


@Validated(G_Phone.Save.class)
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

    @Validated({G_Phone.Save.class, Default.class})
    Number savePhone(@NotNull @Valid Phone phone)
            throws NotUniqueEntityException;
}
