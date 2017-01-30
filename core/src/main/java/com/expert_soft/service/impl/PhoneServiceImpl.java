package com.expert_soft.service.impl;


import com.expert_soft.exception.service.NoSuchEntityException;
import com.expert_soft.exception.service.NotUniqueEntityException;
import com.expert_soft.model.Phone;
import com.expert_soft.persistence.PhoneDao;
import com.expert_soft.service.PhoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.Collection;
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
    public List<Phone> getPhones(Long... keys)  {
        return dao.getPhones(keys);
    }

    @Override
    public List<Phone> getPhones(Collection<Long> keys) {
          return dao.getPhones(keys);
    }

    @Override
    public Phone getPhone(Long id) throws NoSuchEntityException {
        try{
            return dao.getPhone(id);
        } catch (EmptyResultDataAccessException e){
            NoSuchEntityException ex =
                    new NoSuchEntityException("Sorry, but phones in question don't exist", e);
            ex.setFailedEntity("phone with key ");
            throw ex;
        }
    }

    @Override
    public void savePhone(Phone phone) throws NotUniqueEntityException {
        try {
            dao.savePhone(phone);
        } catch (DuplicateKeyException e){
            throw new NotUniqueEntityException("Sorry, but phone with this model exists yet", e);
        }
    }
}
