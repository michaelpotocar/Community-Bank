//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package michaelpotocar.projectkitty.providers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import michaelpotocar.projectkitty.dynamodb.dao.CustomerDao;
import michaelpotocar.projectkitty.dynamodb.model.Customer;
import michaelpotocar.projectkitty.dynamodb.model.CustomerStub;
import michaelpotocar.projectkitty.requests.GetCustomersRequest;
import michaelpotocar.projectkitty.results.GetCustomersResult;

public class GetCustomersProvider implements RequestHandler<GetCustomersRequest, GetCustomersResult> {
    public GetCustomersProvider() {
    }

    public GetCustomersResult handleRequest(GetCustomersRequest input, Context context) {
        System.out.println("handling request1");
        List<Customer> customers = CustomerDao.getAllCustomers();
        GetCustomersResult result = new GetCustomersResult();
        System.out.println("handling request2");
        List<CustomerStub> customerStubs = new ArrayList();
        Iterator var6 = customers.iterator();

        while(var6.hasNext()) {
            Customer c = (Customer)var6.next();
            CustomerStub cs = new CustomerStub();
            cs.setId(c.getId());
            cs.setFirstName(c.getFirstName());
            cs.setLastName(c.getLastName());
            customerStubs.add(cs);
        }

        result.setAccounts(customerStubs);
        System.out.println("handling request3");
        return result;
    }
}
