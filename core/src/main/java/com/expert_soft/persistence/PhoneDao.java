package com.expert_soft.persistence;




import com.expert_soft.model.Phone;

import java.util.List;

public interface PhoneDao {

    Phone getPhone(Long key);
    void savePhone(Phone phone);
    List<Phone> findAll();

}
