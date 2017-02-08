package com.expert_soft.integration;


import com.expert_soft.model.Phone;
import com.expert_soft.persistence.PhoneDao;
import com.expert_soft.test_util.TestConstants;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.WebApplicationContext;

import javax.sql.DataSource;
import java.net.MalformedURLException;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertNotNull;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.MatcherAssert.assertThat;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        TestConstants.SERVLET_CONTEXT})
@WebAppConfiguration
@ActiveProfiles("production")
public class ConnectionFromJarIntTest {

    private static final Logger logger = Logger.getLogger(ConnectionFromJarIntTest.class);

    @Autowired private WebApplicationContext context;
    @Autowired Environment env;


    @Value("${db.url}")
    private String dbUrl;

    @Before
    public void setUp() throws Exception {
        String property = env.getProperty("db.url");
        logger.debug("property from env: " + property);
    }

    @Test
    public void dataSourceInitCorrectly() throws SQLException {
        PhoneDao bean = context.getBean(PhoneDao.class);
        Phone phone = bean.getPhone(1L);
        assertNotNull(phone);
    }

    @Test
    public void assertBeansFromCoreContextSeen(){
        PhoneDao bean = context.getBean(PhoneDao.class);
        Object mapper = context.getBean("order_mapper");
    }

    @Test
    public void dataSource() throws SQLException, MalformedURLException {
        logger.debug("Starting test on dataSource");
        logger.debug("value: " + dbUrl);

        DataSource dataSource = context.getBean(DataSource.class);
        DatabaseMetaData metaData = dataSource.getConnection().getMetaData();
        logger.debug("URL: " + metaData.getURL() + "\n DRIVER " + metaData.getDriverName());

        ResultSet tables = metaData.getTables("PUBLIC", null, null, null);
        List<String> tablesNames = new ArrayList<>();
        while (tables.next()){
            tablesNames.add(tables.getString(3));
        }
        assertThat(tablesNames, hasItems("ORDERS", "ORDER_ITEMS", "PHONES"));
    }
}
