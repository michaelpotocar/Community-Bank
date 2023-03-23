//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package michaelpotocar.projectkitty.providers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import michaelpotocar.projectkitty.dynamodb.dao.CustomerDao;
import michaelpotocar.projectkitty.dynamodb.model.Customer;
import michaelpotocar.projectkitty.requests.GetCustomerInfoRequest;
import michaelpotocar.projectkitty.results.GetCustomerInfoResult;

public class GetCustomerInfoProvider implements RequestHandler<GetCustomerInfoRequest, GetCustomerInfoResult> {
    public GetCustomerInfoProvider() {
    }

    public GetCustomerInfoResult handleRequest(GetCustomerInfoRequest input, Context context) {
        System.out.println("Input: " + input.toString());
        Long customerID = input.getId();
        Customer customer = CustomerDao.getCustomer(customerID);
        System.out.println(customer.toString());
        GetCustomerInfoResult result = new GetCustomerInfoResult();
        result.setId(customer.getId());
        result.setFirstName(customer.getFirstName());
        result.setLastName(customer.getLastName());
        result.setAccounts(customer.getAccounts());
        System.out.println("Result: " + result.toString());
        return result;
    }
}
