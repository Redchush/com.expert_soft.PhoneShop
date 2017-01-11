package com.expert_soft.service.impl;


import com.expert_soft.model.Phone;
import com.expert_soft.persistence.PhoneDao;
import com.expert_soft.service.PhoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhoneServiceImpl implements PhoneService {

    private PhoneDao dao;

    @Autowired
    public void setDao(PhoneDao dao) {
        this.dao = dao;
    }

    @Override
    public List<Phone> findAll() {
        return dao.findAll();
    }

    @Override
    public Phone getPhone(Long id) {
        return dao.getPhone(id);
    }

    @Override
    public void savePhone(Phone phone) {
        dao.savePhone(phone);
    }
}
