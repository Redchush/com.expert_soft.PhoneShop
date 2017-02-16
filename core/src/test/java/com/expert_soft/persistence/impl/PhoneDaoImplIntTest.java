package com.expert_soft.persistence.impl;

import com.expert_soft.model.Phone;
import com.expert_soft.model.order.UserInfoValidationTest;
import com.expert_soft.persistence.PhoneDao;
import com.expert_soft.test_util.Context;
import com.expert_soft.test_util.DataBuilder;
import com.expert_soft.test_util.asserts.Comparators;
import com.expert_soft.test_util.db.DbInfo;
import org.apache.log4j.Logger;
import org.hamcrest.collection.IsEmptyCollection;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.*;

import static com.expert_soft.test_util.asserts.ModelAsserts._assertEquals;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
        Context.ROOT_WITH_CART,
})
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        TransactionalTestExecutionListener.class
})
@ActiveProfiles("test")
@Transactional
public class PhoneDaoImplIntTest {

    private static final Logger LOGGER = Logger.getLogger(UserInfoValidationTest.class);

    @Autowired private PhoneDao dao;
    @Autowired private DataSource dataSource;
    private DbInfo rowCounter;

    private Comparator<Phone> keyComparator;
    private Phone phone_id_1;
    private Phone phone_id_2;

    @Before
    public void setUp() throws Exception {
        rowCounter = new DbInfo(new JdbcTemplate(dataSource));
        phone_id_1 = DataBuilder.getPhoneId_1();
        phone_id_2 = DataBuilder.getPhoneId_2();
        keyComparator =  new Comparators.PhoneByKey();
    }

    @Test
    public void getPhone() throws Exception {
        Phone actual = dao.getPhone(1L);
        Phone expected = DataBuilder.getPhoneId_1();
        _assertEquals(expected, actual);
    }

    @Test
    public void getNotExistingPhone(){
        String errorMessage = "In case of absence of phone must null be returned";
        Phone phone = dao.getPhone(Long.MAX_VALUE);
        assertNull(errorMessage, phone);
        Phone phone1 = dao.getPhone(-1L);
        assertNull(errorMessage, phone1);
    }

    @Test
    public void findAll() throws Exception {
        int rowCountExpected = rowCounter.getPhones();
        List<Phone> all = dao.findAll();
        assertEquals(rowCountExpected, all.size());
    }

    @Test
    public void savePhone() throws Exception {
        Long idExpected = rowCounter.getDynamicPhones() + 1L;

        Phone phoneToBeSaved = DataBuilder.getPhoneToSave(idExpected);
        dao.savePhone(phoneToBeSaved);

        Phone phone = dao.getPhone(idExpected);
        assertEquals(idExpected, phone.getKey());
        _assertEquals(phone, phoneToBeSaved);
    }

    @Test
    @Ignore
    public void saveNotUniquePhone() throws Exception{
        Phone tested = DataBuilder.getPhoneId_1();
        tested.setKey(null);
        Number number = dao.savePhone(tested);
        assertNull("In case of not unique phone must be returned null value", number);

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

    @Test
    public void findAll_checkOrder(){
        List<Phone> actual = dao.findAll();
        List<Phone> ordered = new ArrayList<>(actual);
        Collections.sort(ordered, keyComparator);
        Assert.assertEquals(ordered, actual);

    }


}