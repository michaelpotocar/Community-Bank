//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package michaelpotocar.projectkitty.results;

import java.util.List;
import michaelpotocar.projectkitty.dynamodb.model.CustomerStub;

public class GetCustomersResult {
    private List<CustomerStub> customers;

    public GetCustomersResult() {
    }

    public List<CustomerStub> getCustomers() {
        return this.customers;
    }

    public void setAccounts(List<CustomerStub> customers) {
        this.customers = customers;
    }
}
