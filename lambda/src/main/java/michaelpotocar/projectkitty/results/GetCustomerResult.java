package michaelpotocar.projectkitty.results;

import michaelpotocar.projectkitty.dynamodb.model.Customer;

public class GetCustomerResult {
    private Customer customer;

    public GetCustomerResult() {
    }

    public GetCustomerResult(Customer customer) {
        this.customer = customer;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        return "GetCustomerResult{" +
                "customer=" + customer +
                '}';
    }
}
