package com.expert_soft.service;




import com.expert_soft.model.Phone;

import java.util.List;

public interface PhoneService {

    List<Phone> findAll();
    Phone getPhone(Long id);
    void savePhone(Phone phone);
}
