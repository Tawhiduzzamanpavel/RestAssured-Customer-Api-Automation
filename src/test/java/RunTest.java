import org.apache.commons.configuration.ConfigurationException;
import org.junit.Test;

import java.io.IOException;

public class RunTest {
    @Test
    public void callingLoginAPI() throws ConfigurationException, IOException {
        Customer customer = new Customer();
        customer.callingLoginAPI();

    }
    @Test
    public void getList() throws ConfigurationException, IOException {
        Customer customer = new Customer();
        customer.customerList();

    }
    @Test
    public void search() throws ConfigurationException, IOException {
        Customer customer = new Customer();
        customer.searchCustomer();

    }
    @Test
    public void newcreate() throws ConfigurationException, IOException {
        Customer customer = new Customer();
        customer.NewCustomer();

    }

    @Test
    public void create() throws ConfigurationException, IOException {
        Customer customer = new Customer();
        customer.createCustomer();

    }
    @Test
    public void update() throws ConfigurationException, IOException {
        Customer customer = new Customer();
        customer.updateCustomer();

    }
    @Test
    public void delete() throws ConfigurationException, IOException {
        Customer customer = new Customer();
        customer.deleteCustomer();

    }


}

