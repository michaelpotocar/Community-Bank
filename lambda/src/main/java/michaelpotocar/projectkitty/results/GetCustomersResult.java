package michaelpotocar.projectkitty.results;

import java.util.List;

import michaelpotocar.projectkitty.dynamodb.model.CustomerStub;

public class GetCustomersResult {
    private List<CustomerStub> customers;

    public GetCustomersResult() {
    }

    public GetCustomersResult(List<CustomerStub> customers) {
        this.setCutomers(customers);
    }

    public List<CustomerStub> getCustomers() {
        return this.customers;
    }

    public void setCutomers(List<CustomerStub> customers) {
        this.customers = customers;
    }

    @Override
    public String toString() {
        return "GetCustomersResult{" +
                "customers=" + customers +
                '}';
    }
}
