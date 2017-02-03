package com.expert_soft.service.impl;


import com.expert_soft.model.Phone;
import com.expert_soft.persistence.PhoneDao;
import com.expert_soft.service.PhoneService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;



@Service("phoneService")
public class PhoneServiceImpl implements PhoneService {

    private PhoneDao dao;

    public void setDao(PhoneDao dao) {
        this.dao = dao;
    }

    @Override
    public List<Phone> findAll() {
        return dao.findAll();
    }

    @Override
    public List<Phone> getPhones(Long... keys)  {
        return dao.getPhones(keys);
    }

    @Override
    public List<Phone> getPhones(Collection<Long> keys) {
        return dao.getPhones(keys);
    }

    @Override
    public Phone getPhone(Long key) {
        return dao.getPhone(key);
    }

    @Override
    public Number savePhone(Phone phone) {
       return dao.savePhone(phone);
    }
}
