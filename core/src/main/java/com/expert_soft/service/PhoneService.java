package com.expert_soft.service;




import com.expert_soft.exception.service.NotUniqueEntityException;
import com.expert_soft.model.Phone;
import com.expert_soft.validator.group.G_Phone;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;
import java.util.Collection;
import java.util.List;


@Validated(G_Phone.Save.class)
public interface PhoneService {

    List<Phone> findAll();

    /**
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

    @Validated(Default.class)
    Phone getPhone(@NotNull
                   @Min(value = 1, message = "{common.key}")
                   Long id);

    @Validated({G_Phone.Save.class, Default.class})
    void savePhone(@NotNull @Valid Phone phone)
            throws NotUniqueEntityException;
}
