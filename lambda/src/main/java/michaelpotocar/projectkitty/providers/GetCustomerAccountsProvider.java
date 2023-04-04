package michaelpotocar.projectkitty.providers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import michaelpotocar.projectkitty.dynamodb.dao.CustomerDao;
import michaelpotocar.projectkitty.dynamodb.model.Account;
import michaelpotocar.projectkitty.dynamodb.model.Customer;
import michaelpotocar.projectkitty.requests.GetCustomerAccountsRequest;
import michaelpotocar.projectkitty.results.GetCustomerAccountsResult;

import java.util.List;

public class GetCustomerAccountsProvider implements RequestHandler<GetCustomerAccountsRequest, GetCustomerAccountsResult> {
    public GetCustomerAccountsProvider() {
    }

    public GetCustomerAccountsResult handleRequest(GetCustomerAccountsRequest input, Context context) {
        System.out.println("Input: " + input.toString());
        Long customerId = input.getCustomerId();

        Customer customer = CustomerDao.getCustomer(customerId);

        if (customer == null) {
            GetCustomerAccountsResult result = new GetCustomerAccountsResult(null);
            System.out.println("No Customer Exists: " + result);
            return result;
        }

        List<Account> accounts = customer.getAccounts();

        GetCustomerAccountsResult result = new GetCustomerAccountsResult(accounts);
        System.out.println("Result: " + result);
        return result;
    }
}
