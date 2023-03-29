package michaelpotocar.projectkitty.providers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import michaelpotocar.projectkitty.dynamodb.dao.CustomerDao;
import michaelpotocar.projectkitty.dynamodb.model.Customer;
import michaelpotocar.projectkitty.requests.GetAccountsRequest;
import michaelpotocar.projectkitty.results.GetAccountsResult;

public class GetAccountsProvider implements RequestHandler<GetAccountsRequest, GetAccountsResult> {
    public GetAccountsProvider() {
    }

    public GetAccountsResult handleRequest(GetAccountsRequest input, Context context) {
        System.out.println("Input: " + input.toString());
        Long customerID = input.getCustomerId();
        Customer customer = CustomerDao.getCustomer(customerID);

        GetAccountsResult result = new GetAccountsResult();
        result.setCustomerId(customer.getId());
        result.setFirstName(customer.getFirstName());
        result.setLastName(customer.getLastName());
        result.setAccounts(customer.getAccounts());

        System.out.println("Result: " + result.toString());
        return result;
    }
}
