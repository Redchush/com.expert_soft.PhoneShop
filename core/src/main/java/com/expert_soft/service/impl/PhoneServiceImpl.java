package com.expert_soft.service.impl;


import com.expert_soft.model.Phone;
import com.expert_soft.persistence.PhoneDao;
import com.expert_soft.service.PhoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;



@Service("phoneService")
public class PhoneServiceImpl implements PhoneService {

    private PhoneDao phoneDao;

    @Override
    public List<Phone> findAll() {
        return phoneDao.findAll();
    }

    @Override
    public List<Phone> getPhones(Long... keys)  {
        return phoneDao.getPhones(keys);
    }

    @Override
    public List<Phone> getPhones(Collection<Long> keys) {
        return phoneDao.getPhones(keys);
    }

    @Override
    public Phone getPhone(Long key) {
        return phoneDao.getPhone(key);
    }

    @Override
    public Number savePhone(Phone phone) {
       return phoneDao.savePhone(phone);
    }

    @Autowired
    public void setPhoneDao(PhoneDao dao) {
        this.phoneDao = dao;
    }
}
