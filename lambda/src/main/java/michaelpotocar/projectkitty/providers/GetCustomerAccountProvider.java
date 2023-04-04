package michaelpotocar.projectkitty.providers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import michaelpotocar.projectkitty.dynamodb.dao.CustomerDao;
import michaelpotocar.projectkitty.dynamodb.model.Account;
import michaelpotocar.projectkitty.dynamodb.model.Customer;
import michaelpotocar.projectkitty.requests.GetCustomerAccountRequest;
import michaelpotocar.projectkitty.results.GetCustomerAccountResult;

import java.util.List;
import java.util.stream.Collectors;

public class GetCustomerAccountProvider implements RequestHandler<GetCustomerAccountRequest, GetCustomerAccountResult> {
    public GetCustomerAccountProvider() {
    }

    public GetCustomerAccountResult handleRequest(GetCustomerAccountRequest input, Context context) {
        System.out.println("Input: " + input.toString());
        Long customerId = input.getCustomerId();
        String accountId = input.getAccountId();

        Customer customer = CustomerDao.getCustomer(customerId);

        if (customer == null) {
            GetCustomerAccountResult result = new GetCustomerAccountResult(null);
            System.out.println("No Customer Exists: " + result);
            return result;
        }

        List<Account> accounts = customer.getAccounts().stream().filter(a -> a.getAccountId().equals(accountId)).collect(Collectors.toList());

        if (accounts.size() == 0) {
            GetCustomerAccountResult result = new GetCustomerAccountResult(null);
            System.out.println("Result: " + result);
            return result;
        }

        GetCustomerAccountResult result = new GetCustomerAccountResult(accounts.get(0));
        System.out.println("Result: " + result);
        return result;

    }
}
