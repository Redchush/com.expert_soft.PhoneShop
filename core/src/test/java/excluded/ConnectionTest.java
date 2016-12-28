package excluded;

import com.expert_soft.model.Phone;
import com.expert_soft.persistence.PhoneDao;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
        "classpath:persistence-context.xml",
        "classpath:test-dataSource-context.xml"
//        ,"classpath:test-transaction-context.xml"

})

@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class
        ,TransactionalTestExecutionListener.class
} )
@Transactional
public class ConnectionTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private PhoneDao dao;

    @Test
    public void setDataSource() throws Exception {
        DataSource dataSource = jdbcTemplate.getDataSource();
        DatabaseMetaData metaData;
        String databaseProductName;
        try (Connection con = dataSource.getConnection()) {
            metaData = con.getMetaData();
            databaseProductName = metaData.getDatabaseProductName();
            System.out.println(databaseProductName);
            try (ResultSet catalogs = metaData.getCatalogs();) {

                while (catalogs.next()) {
                    String object = catalogs.getString(1);
                    System.out.println(object);
                    try (ResultSet columns = metaData.getColumns(object, null, null, null)) {
                        while (columns.next()){
                            for (int i = 1; i < 24; i++) {
                                Object object1 = columns.getObject(i);
                                System.out.print(object1 + " ");
                            }
                            System.out.println();
                        }
                    }
                }
            }

            System.out.println();

        }
        assertEquals(dataSource, dataSource);
    }


}

