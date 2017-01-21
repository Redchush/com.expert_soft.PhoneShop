package com.expert_soft.persistence.impl;

import com.expert_soft.model.Phone;
import com.expert_soft.model.UserInfoValidationTest;
import com.expert_soft.persistence.PhoneDao;
import org.apache.log4j.Logger;
import org.hamcrest.collection.IsEmptyCollection;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import util.CountRowResponsible;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
        "classpath:persistence-context.xml",
        "classpath:test-dataSource-context.xml",
        "classpath:core_test-bean.xml"
})
@TestPropertySource(locations =
        "classpath:config/applicationTest.properties")
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        TransactionalTestExecutionListener.class
})
@Transactional
public class PhoneDaoImplTest {



    private static final Logger LOGGER = Logger.getLogger(UserInfoValidationTest.class);

    @Autowired ApplicationContext apCtx;
    @Autowired PhoneDao dao;
    @Autowired CountRowResponsible rowCounter;

    @Autowired @Qualifier("phone_id_1") Phone phone_id_1;
    @Autowired @Qualifier("phone_id_2") Phone phone_id_2;


    @Test
    public void setDataSource() throws Exception {}

    @Test
    public void getPhone() throws Exception {
        Phone actual = dao.getPhone(1L);
        assertEquals(phone_id_1, actual);
    }

    @Test(expected = org.springframework.dao.EmptyResultDataAccessException.class)
    public void getNotExistingPhone(){
        dao.getPhone(Long.MAX_VALUE);
    }

    @Test
    public void findAll() throws Exception {
        int rowCountExpected = rowCounter.getPhones();
        List<Phone> all = dao.findAll();
        assertEquals(rowCountExpected, all.size());
    }

    @Test
    @Ignore
    public void savePhone() throws Exception {
        Long idExpected = rowCounter.getPhones() + 1L;
        Phone phoneToBeSaved = (Phone) apCtx.getBean("phone_to_save_1");
        dao.savePhone(phoneToBeSaved);

        Phone phone = dao.getPhone(idExpected);
        assertEquals(idExpected, phone.getKey());

        phoneToBeSaved.setKey(idExpected);
        assertEquals(phone, phoneToBeSaved);
    }

    @Test(expected = org.springframework.dao.DuplicateKeyException.class)
    public void saveNotUniquePhone(){
        phone_id_1.setKey(null);
        dao.savePhone(phone_id_1);
    }

    @Test
    public void getPhonesWithArray() throws Exception {
        List<Phone> actual = dao.getPhones(1L, 2L);
        assertEquals("List size expected", 2, actual.size());
        assertThat(actual, contains(phone_id_1, phone_id_2));
    }

    @Test
    public void getPhonesWithCollection() throws Exception {
        List<Phone> actual = dao.getPhones(Arrays.asList(1L, 2L));
        assertEquals("List size expected", 2, actual.size());
        assertThat(actual, contains(phone_id_1, phone_id_2));

        List<Phone> actual_2 = dao.getPhones(new HashSet<Long>(Arrays.asList(1L, 2L)));
        assertEquals("List size expected", 2, actual_2.size());
        assertThat(actual_2, contains(phone_id_1, phone_id_2));
    }

    @Test
    public void getPhonesWithArray_NULL() throws Exception{
        List<Phone> actual = dao.getPhones(new HashSet<Long>());
        assertThat(actual, is(IsEmptyCollection.empty()));

    }
}