package integration;


import com.expert_soft.config.ApplicationConfiguration;
import com.expert_soft.controller.ProductController;
import com.expert_soft.model.Phone;
import com.expert_soft.persistence.PhoneDao;
import com.expert_soft.service.PhoneService;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
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
@ContextConfiguration(classes = {ApplicationConfiguration.class} )
@WebAppConfiguration
public class ConnectionFromJarTest {

    private static final Logger logger = Logger.getLogger(ProductController.class);

    @Autowired
    private WebApplicationContext context;

    @Mock
    private PhoneService sampleService;

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
        DataSource dataSource = (DataSource) context.getBean("schema");
        DatabaseMetaData metaData = dataSource.getConnection().getMetaData();
        logger.debug("URL: " + metaData.getURL());
        logger.debug("Path: " + metaData.getURL());

        ResultSet tables = metaData.getTables("PUBLIC", null, null, null);
        List<String> tablesNames = new ArrayList<>();
        while (tables.next()){
            tablesNames.add(tables.getString(3));
        }
        assertThat(tablesNames, hasItems("ORDERS", "ORDER_ITEMS", "PHONES"));

    }

}
