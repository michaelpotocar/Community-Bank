package michaelpotocar.projectkitty.results;

import java.util.List;

import michaelpotocar.projectkitty.dynamodb.model.Customer;

public class GetCustomersResult {
    private List<Customer> customers;

    public GetCustomersResult() {
    }

    public GetCustomersResult(List<Customer> customers) {
        this.customers = customers;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    @Override
    public String toString() {
        return "GetCustomersResult{" +
                "customers=" + customers +
                '}';
    }
}
