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
        System.out.println("Input: " + input.toString());
        List<Customer> customers = CustomerDao.getAllCustomers();

        List<CustomerStub> customerStubs = new ArrayList();
        for(Customer customer : customers) {
            CustomerStub cs = new CustomerStub();
            cs.setId(customer.getId());
            cs.setFirstName(customer.getFirstName());
            cs.setLastName(customer.getLastName());
            customerStubs.add(cs);
        }

        GetCustomersResult result = new GetCustomersResult(customerStubs);

        System.out.println("Result: " + result.toString());
        return result;
    }
}
